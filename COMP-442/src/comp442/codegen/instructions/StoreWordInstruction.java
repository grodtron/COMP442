package comp442.codegen.instructions;

import comp442.codegen.Register;

public class StoreWordInstruction extends Instruction {

	private final Register destAddress;
	private final int      offset;
	private final Register value;
	
	public StoreWordInstruction(Register baseAddress, int offset, Register value) {
		this.destAddress = baseAddress;
		this.offset      = offset;
		this.value       = value;
	}

	@Override
	protected String _getCode() {
		return "sw" + '\t' + offset + "(" + destAddress.registerName + "), " + value.registerName;
	}

}
