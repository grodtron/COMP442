package comp442.codegen.instructions;

import comp442.codegen.Register;

public class ImmediateMathOperationInstruction extends Instruction {

	private String opcode;
	private Register dest;
	private Register src;
	private int k;
	
	public ImmediateMathOperationInstruction(String opcode, Register dest, Register src, int k) {
		this.opcode = opcode;
		this.dest   = dest;
		this.src    = src;
		this.k      = k;
	}

	@Override
	protected String _getCode() {
		return opcode + '\t' + dest.registerName + ", " + src.registerName + ", " + k;
	}

}
