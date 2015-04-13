package comp442.codegen.instructions;

import comp442.codegen.Register;

public class GetInstruction extends Instruction {

	Register destination;
	
	public GetInstruction(Register destination) {
		this.destination = destination;
	}
	
	@Override
	protected String _getCode() {
		return "getc\t" + destination.registerName;
	}

}
