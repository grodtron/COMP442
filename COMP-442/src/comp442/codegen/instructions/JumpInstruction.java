package comp442.codegen.instructions;

public class JumpInstruction extends Instruction {

	private String label;
	
	public JumpInstruction(String label) {
		this.label = label;
	}
	
	@Override
	protected String _getCode() {
		return "j\t" + label;
	}

}
