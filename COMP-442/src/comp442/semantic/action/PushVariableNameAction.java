package comp442.semantic.action;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.VariableBuilder;

public class PushVariableNameAction extends SemanticAction {
	
	@Override
	public void execute(Token precedingToken) throws CompilerError {
		if(context.variableBuilder == null){
			System.out.println("push variable - new builder for " + precedingToken.lexeme);
			context.variableBuilder = new VariableBuilder(precedingToken.lexeme);
		}else{
			System.out.println("push variable - for " + precedingToken.lexeme);
			context.variableBuilder.pushId(precedingToken.lexeme);
		}
	}

}
