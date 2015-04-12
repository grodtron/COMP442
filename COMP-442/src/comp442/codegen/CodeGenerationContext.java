package comp442.codegen;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import comp442.codegen.instructions.Instruction;
import comp442.error.InternalCompilerError;

public class CodeGenerationContext {

	private Set<Register> temporaryRegisters;
	private List<Instruction> instructions;
	
	
	public CodeGenerationContext(){
		temporaryRegisters = new HashSet<Register>();
		temporaryRegisters.addAll(Register.unallocatedRegisters);
		
		instructions = new ArrayList<Instruction>();
	}
	
	public Register getTemporaryRegister() {
		Register temp = temporaryRegisters.iterator().next();
		temporaryRegisters.remove(temp);
		
		return temp;
	}
	
	public void freeTemporaryRegister(Register temp) throws InternalCompilerError{
		boolean alreadyExisted = ! temporaryRegisters.add(temp);
		
		if(alreadyExisted){
			throw new InternalCompilerError("double free of a temp register!");
		}
	}

	public void appendInstruction(Instruction instr) {
		instructions.add(instr);
	}

	public void printCode(PrintStream out) {
		for(Instruction i : instructions){
			out.println(i.getCode());
		}
	}

}
