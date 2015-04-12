package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;
import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;


public class VoidValue implements Value {

	private VoidValue(){}
	
	private static VoidValue instance = new VoidValue();
	
	public static VoidValue get(){
		return instance;
	}

	@Override
	public Value getUseableValue(CodeGenerationContext c) throws InternalCompilerError {
		throw new InternalCompilerError("Cannot get value of a Void Expression");
	}

	@Override
	public boolean isStatic() {
		return false;
	}

	@Override
	public RegisterValue getRegisterValue(CodeGenerationContext c) throws CompilerError {
		throw new InternalCompilerError("Cannot get register value of a Void Expression");
	}
}
