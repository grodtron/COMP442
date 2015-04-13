package comp442.codegen.instructions;

public class NoopInstruction extends Instruction {

	@Override
	protected String _getCode() {
		return "nop";
	}

}
