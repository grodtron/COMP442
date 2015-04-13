package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;
import comp442.error.CompilerError;

public abstract class LateBindingDynamicValue extends DynamicValue {

	public abstract DynamicValue get() throws CompilerError;
	
	@Override
	public Value getUseableValue(CodeGenerationContext c) throws CompilerError {
		return get().getUseableValue(c);
	}

	@Override
	public RegisterValue getRegisterValue(CodeGenerationContext c) throws CompilerError {
		return get().getRegisterValue(c);
	}

}
