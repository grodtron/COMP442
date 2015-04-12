package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;

public class PushIntLiteralAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		try{
			context.getCurrent().pushIntLiteral(Integer.valueOf(precedingToken.lexeme));
		}catch(NumberFormatException e){
			throw new CompilerError("Invalid int literal: " + e.getMessage());
		}
	}

}
