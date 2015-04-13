package comp442.codegen.instructions;

import comp442.codegen.Register;

public class PutInstruction extends Instruction {

	private Register source;
	
	public PutInstruction(Register source) {
		this.source = source;
	}
	
	@Override
	protected String _getCode() {
		return "putc\t" + source.registerName;
	}

}
