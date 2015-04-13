package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;
import comp442.error.CompilerError;

public abstract class LateBindingStaticIntValue implements Value {

	public abstract int get();
	
	@Override
	public Value getUseableValue(CodeGenerationContext c) throws CompilerError {
		return new StaticIntValue(get());
	}

	@Override
	public boolean isStatic() {
		return true;
	}

	@Override
	public RegisterValue getRegisterValue(CodeGenerationContext c) throws CompilerError {
		return getUseableValue(c).getRegisterValue(c);
	}

}
