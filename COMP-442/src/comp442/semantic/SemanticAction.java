package comp442.semantic;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;

public interface SemanticAction {

	public abstract void execute(Token precedingToken) throws CompilerError;
	
}
