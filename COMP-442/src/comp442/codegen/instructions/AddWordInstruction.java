package comp442.codegen.instructions;

import comp442.codegen.Register;
import comp442.semantic.value.RegisterValue;

public class AddWordInstruction extends Instruction {

	private final Register dest;
	private final Register a;
	private final Register b;
	
	public AddWordInstruction(RegisterValue destVal, RegisterValue aVal, RegisterValue bVal) {
		dest = destVal.getRegister();
		a    = aVal.getRegister();
		b    = bVal.getRegister();
	}

	public AddWordInstruction(Register destVal, Register aVal, Register bVal) {
		dest = destVal;
		a    = aVal;
		b    = bVal;
	}

	@Override
	protected String _getCode() {
		return "add" + '\t' + dest.registerName + ", " + a.registerName + ", " + b.registerName;
	}

}
