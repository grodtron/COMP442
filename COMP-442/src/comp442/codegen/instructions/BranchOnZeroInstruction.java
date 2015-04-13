package comp442.codegen.instructions;

import comp442.codegen.Register;

public class BranchOnZeroInstruction extends Instruction {

	private String label;
	private Register register;
	
	public BranchOnZeroInstruction(Register register, String label) {
		this.label = label;
		this.register = register;
	}
	
	@Override
	protected String _getCode() {
		return "bz" + '\t' + register.registerName + ", " + label;
	}

}
