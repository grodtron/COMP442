package comp442.codegen;

import java.io.PrintStream;

import comp442.codegen.instructions.AddWordImmediateInstruction;
import comp442.codegen.instructions.AlignInstruction;
import comp442.codegen.instructions.EntryInstruction;
import comp442.codegen.instructions.HaltInstruction;
import comp442.codegen.instructions.JumpRegisterInstruction;
import comp442.codegen.instructions.LoadWordInstruction;
import comp442.codegen.instructions.StoreWordInstruction;
import comp442.error.CompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.symboltable.SymbolContext;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.ClassEntry;
import comp442.semantic.symboltable.entries.FunctionEntry;
import comp442.semantic.symboltable.entries.SymbolTableEntry;

public class CodeGenerator {
	
	private PrintStream output;
	
	public CodeGenerator(PrintStream output){
		this.output = output;
	}
	
	public void generate() throws CompilerError{
		System.out.println("=== beginning code generation ===");
		
		SymbolTable s = SymbolContext.getCurrentScope();
		
		generate(s);
		
		SymbolTableEntry program = s.find("program");
		
		generateProgram((FunctionEntry) program);
		
	}
	
	private void generate(SymbolTable s) throws CompilerError{
		for(SymbolTableEntry e : s.getEntries()){
			if(e instanceof FunctionEntry && e.getName() != "program"){
				generateFunction((FunctionEntry) e);
			}
			if(e instanceof ClassEntry){
				generate(e.getScope());
			}
		}		
	}
	
	private void generateProgram(FunctionEntry program) throws CompilerError{
		
		CodeGenerationContext c = new CodeGenerationContext();
			
		String stackLabel = "stack";
		
		c.appendInstruction(new EntryInstruction().setComment("==== Program Begin ===="));
		c.appendInstruction(new AddWordImmediateInstruction(Register.STACK_POINTER, Register.ZERO, stackLabel));
		c.appendInstruction(new AddWordImmediateInstruction(Register.STACK_POINTER, Register.STACK_POINTER, program.getScope().getSize()).setComment("init stack pointer"));
		
		for(Statement s : program.getStatements()){
			c.commentNext(s.getClass().getSimpleName());
			s.generateCode(c);
		}
		
		c.appendInstruction(new HaltInstruction().setComment("==== Program End ===="));
		
		c.appendInstruction(new AlignInstruction().setLabel(stackLabel).setComment("start of stack"));
		c.printCode(output);
	}
	
	private void generateFunction(FunctionEntry func) throws CompilerError{
		CodeGenerationContext c = new CodeGenerationContext();
		SymbolTable s = func.getScope();
		
		int returnAddrMemOffset =  -s.getSize() + s.find(SpecialValues.RETURN_ADDRESS_PARAMETER_NAME).getOffset();
		
		c.labelNext(func.getLabel());
		c.commentNext(func.toString());
		
		c.appendInstruction(new StoreWordInstruction(Register.STACK_POINTER, returnAddrMemOffset, Register.RETURN_ADDRESS)
				.setComment("Store return address"));
		
		for(Statement stat : func.getStatements()){
			c.commentNext(stat.getClass().getSimpleName());
			stat.generateCode(c);
		}
				
		Register pcRegister = c.getTemporaryRegister();
		c.appendInstruction(new LoadWordInstruction(pcRegister, Register.STACK_POINTER, returnAddrMemOffset)
				.setComment("get return address"));
		// reset the stackPointer!!
		c.appendInstruction(new AddWordImmediateInstruction(Register.STACK_POINTER, Register.STACK_POINTER, -1 * s.getSize())
			.setComment("reset stack pointer"));
		c.appendInstruction(new JumpRegisterInstruction(pcRegister)
				.setComment("return"));
		
		c.printCode(output);
		
	}
	
}
