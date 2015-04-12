package comp442.codegen.instructions;

import comp442.codegen.Register;
import comp442.error.CompilerError;

public class AddWordImmediateInstruction extends Instruction {

	private final Register dest;
	private final Register regVal;
	private final int      iVal;
	
	public AddWordImmediateInstruction(Register dest, Register regVal, int iVal) throws CompilerError{
		this.dest   = dest;
		this.regVal = regVal;
		this.iVal   = iVal;
		
		if(iVal >= (1<<16)){
			throw new CompilerError("Immediate values greater than " + ((1<<16)-1) + " are not supported");
		}
	}
	
	@Override
	protected String _getCode() {
		return "addi" + '\t' + dest.registerName + ", " + regVal.registerName + ", " + iVal;
	}

}
