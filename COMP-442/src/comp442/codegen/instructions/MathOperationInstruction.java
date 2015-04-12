package comp442.codegen.instructions;

import comp442.codegen.Register;

public class MathOperationInstruction extends Instruction {

	private String opcode;
	private Register dest;
	private Register a;
	private Register b;
	
	public MathOperationInstruction(String opcode, Register dest, Register a, Register b) {
		this.opcode = opcode;
		this.dest   = dest;
		this.a      = a;
		this.b      = b;
	}

	@Override
	protected String _getCode() {
		return opcode + '\t' + dest.registerName + ", " + a.registerName + ", " + b.registerName;
	}

}
