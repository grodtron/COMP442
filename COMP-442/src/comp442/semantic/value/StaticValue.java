package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;
import comp442.error.CompilerError;

public abstract class StaticValue implements Value {

	public abstract int intValue();
	
	public abstract float floatValue();
	
	@Override
	public Value getUseableValue(CodeGenerationContext c) throws CompilerError {
		return this;
	}
	
	@Override
	public boolean isStatic() {
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof StaticValue && intValue() == ((StaticValue)obj).intValue();
	}

	
}
