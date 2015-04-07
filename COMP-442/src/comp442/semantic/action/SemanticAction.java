package comp442.semantic.action;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;

public abstract class SemanticAction {

	protected final static SemanticContext context = SemanticContext.instance;
	
	public abstract void execute(Token precedingToken) throws CompilerError;
	
}
