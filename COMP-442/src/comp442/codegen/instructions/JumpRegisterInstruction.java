package comp442.codegen.instructions;

import comp442.codegen.Register;

public class JumpRegisterInstruction extends Instruction {

	private final Register register;
	
	public JumpRegisterInstruction(Register register) {
		this.register = register;
	}
	
	@Override
	protected String _getCode() {
		return "jr\t" + register.registerName;
	}

}
