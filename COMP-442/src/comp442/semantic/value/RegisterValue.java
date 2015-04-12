package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.error.CompilerError;

public class RegisterValue extends DynamicValue {

	private final Register register;
	
	public RegisterValue(Register reg){
		this.register = reg;
	}

	public Register getRegister() {
		return register;
	}
	
	@Override
	public String toString() {
		return register.symbolicName;
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof RegisterValue && ((RegisterValue)other).getRegister().equals(register);
	}

	@Override
	public Value getUseableValue(CodeGenerationContext c) {
		return this;
	}

	@Override
	public RegisterValue getRegisterValue(CodeGenerationContext c) throws CompilerError {
		return this;
	}
	
}
