package comp442.semantic.value;

import comp442.codegen.Register;

public class RegisterValue implements Value {

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
	
}
