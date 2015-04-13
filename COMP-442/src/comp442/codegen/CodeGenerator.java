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
		
		c.appendInstruction(new EntryInstruction());
		c.appendInstruction(new AddWordImmediateInstruction(Register.STACK_POINTER, Register.ZERO, stackLabel));
		c.appendInstruction(new AddWordImmediateInstruction(Register.STACK_POINTER, Register.STACK_POINTER, program.getScope().getSize()));
		
		for(Statement s : program.getStatements()){
			s.generateCode(c);
		}
		
		c.appendInstruction(new HaltInstruction());
		
		c.appendInstruction(new AlignInstruction().setLabel(stackLabel));
		c.printCode(output);
	}
	
	private void generateFunction(FunctionEntry func) throws CompilerError{
		CodeGenerationContext c = new CodeGenerationContext();
		SymbolTable s = func.getScope();
		
		int returnAddrMemOffset = s.find(SpecialValues.RETURN_ADDRESS_PARAMETER_NAME).getOffset();
		
		c.labelNext(func.getLabel());
		
		// store the return address properly
		c.appendInstruction(new StoreWordInstruction(Register.STACK_POINTER, returnAddrMemOffset, Register.RETURN_ADDRESS));
		
		for(Statement stat : func.getStatements()){
			stat.generateCode(c);
		}
		
		// TODO - return statement!!! needs to store result into RETURN_VALUE and then never touch it again
		
		Register pcRegister = c.getTemporaryRegister();
		c.appendInstruction(new LoadWordInstruction(pcRegister, Register.STACK_POINTER, returnAddrMemOffset));
		// reset the stackPointer!!
		c.appendInstruction(new AddWordImmediateInstruction(Register.STACK_POINTER, Register.STACK_POINTER, -1 * s.getSize()));
		c.appendInstruction(new JumpRegisterInstruction(pcRegister));
		
		c.printCode(output);
		
	}
	
}
