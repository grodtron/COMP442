package comp442.codegen.instructions;

import comp442.codegen.Register;
import comp442.semantic.value.RegisterValue;
import comp442.semantic.value.StaticValue;

public class LoadWordInstruction extends Instruction {

	private final Register destReg;
	private final Register sourceReg;
	private final int 	   offset;
	
	public LoadWordInstruction(RegisterValue destination, RegisterValue baseAddress, StaticValue offsetValue) {
		destReg   = destination.getRegister();
		sourceReg = baseAddress.getRegister();
		offset   = offsetValue.intValue();
	}

	@Override
	protected String _getCode() {
		return "lw" + '\t' + destReg.registerName + ", " + offset + "(" + sourceReg.registerName + ")";
	}

}
