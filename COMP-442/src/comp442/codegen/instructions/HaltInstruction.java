package comp442.codegen.instructions;

public class HaltInstruction extends Instruction {

	@Override
	protected String _getCode() {
		return "hlt";
	}

}
