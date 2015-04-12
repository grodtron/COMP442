package comp442.semantic.statement;

import comp442.codegen.CodeGenerationContext;
import comp442.error.CompilerError;

public interface Statement {

	public void generateCode(CodeGenerationContext c) throws CompilerError;
	
	public String pseudoCode();
	
}
