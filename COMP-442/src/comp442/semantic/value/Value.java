package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;
import comp442.error.CompilerError;

public interface Value {

	public Value getUseableValue(CodeGenerationContext c) throws CompilerError;

	public boolean isStatic();

	/**
	 * Generate code to convert this value into a Register value.
	 * In the case of a dynamic (run-time) value, this is the same as getUseableValue
	 * 
	 * In the case of a static (compile-time) value, this is the same as calling
	 * getUseableValue and then storing the result in a register.
	 * 
	 * @param c The current code generation context
	 * 
	 * @return A dynamic version of this value
	 * @throws CompilerError 
	 */
	public RegisterValue getRegisterValue(CodeGenerationContext c) throws CompilerError;
	
}
