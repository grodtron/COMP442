package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;
import comp442.error.CompilerError;

public class IndirectValue extends DynamicValue {

	Value v;
	
	public IndirectValue(Value value) {
		this.v = value;
	}

	@Override
	public Value getUseableValue(CodeGenerationContext c) throws CompilerError {
		return v.getUseableValue(c);
	}

	@Override
	public RegisterValue getRegisterValue(CodeGenerationContext c) throws CompilerError {
		return v.getRegisterValue(c);
	}



}
