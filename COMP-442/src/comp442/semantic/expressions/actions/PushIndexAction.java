package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;

public class PushIndexAction extends ExpressionAction {

	@Override
	public void execute(Token precedingToken) throws CompilerError {
		System.out.println(" push index! " + precedingToken.lexeme);
		context.getCurrent().pushIndex(precedingToken.lexeme);

		
//		if(context.variableBuilder == null){
//			throw new RuntimeException("Something has gone very wrong in the syntactic/lexical stage");
//		}else{
//			System.out.println("push index " + precedingToken.lexeme);
//			try{
//				// Arrays are 1-indexed, but internally we will zero index, cause 1-indexing is suuuuper retarded
//				context.variableBuilder.pushIndex(Integer.parseInt(precedingToken.lexeme) - 1);
//			}catch(NumberFormatException e){
//				throw new NotYetSupportedError("Non-integer literal indexing not yet supported");
//			}
//		}
	}

}
