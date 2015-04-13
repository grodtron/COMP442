package comp442.codegen.instructions;

import comp442.codegen.Register;

public class JumpAndLinkInstruction extends Instruction {

	private final Register linkRegister;
	private final String   label;
	
	public JumpAndLinkInstruction(Register returnAddress, String callingLabel) {
		this.linkRegister = returnAddress;
		this.label        = callingLabel;
	}

	@Override
	protected String _getCode() {
		return "jl\t" + linkRegister.registerName + ", " + label;
	}

}
