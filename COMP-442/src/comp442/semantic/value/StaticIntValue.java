package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.codegen.instructions.AddWordImmediateInstruction;
import comp442.error.CompilerError;

public class StaticIntValue extends StaticValue implements Value {

	private final int v;
	
	public StaticIntValue(int v){
		this.v = v;
	}
	
	@Override
	public int intValue() {
		return v;
	}

	@Override
	public float floatValue() {
		return (float) v;
	}

	@Override
	public String toString() {
		return Integer.toString(v);
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof StaticIntValue && ((StaticIntValue)other).intValue() == v;
	}

	@Override
	public RegisterValue getRegisterValue(CodeGenerationContext c) throws CompilerError {
		RegisterValue reg = new RegisterValue(c.getTemporaryRegister());
		
		c.appendInstruction(new AddWordImmediateInstruction(reg.getRegister(), Register.ZERO, v));
		
		return reg;
	}
	
}
