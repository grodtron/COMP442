package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;

public abstract class StaticValue implements Value {

	public abstract int intValue();
	
	public abstract float floatValue();
	
	@Override
	public Value getUseableValue(CodeGenerationContext c) {
		return this;
	}
	
	@Override
	public boolean isStatic() {
		return true;
	}

	
}
