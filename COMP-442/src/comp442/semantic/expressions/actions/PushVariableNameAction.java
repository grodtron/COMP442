package comp442.semantic.expressions.actions;

import comp442.error.CompilerError;
import comp442.lexical.token.Token;
import comp442.semantic.expressions.ExpressionAction;
import comp442.semantic.expressions.ExpressionElement;
import comp442.semantic.expressions.VariableBuilder;


public class PushVariableNameAction extends ExpressionAction {
	
	@Override
	public void execute(Token precedingToken) throws CompilerError {
		System.out.println("push id! " + precedingToken.lexeme);
		ExpressionElement e = context.getCurrent();
		
		if(e == null){
			System.out.println("  create new!");
			e = new VariableBuilder(precedingToken.lexeme);
			context.pushChild(e);
		}else{
			e.pushIdentifier(precedingToken.lexeme);
		}
		
//		if(context.variableBuilder == null){
//			System.out.println("push variable - new builder for " + precedingToken.lexeme);
//			context.variableBuilder = new VariableBuilder(precedingToken.lexeme);
//		}else{
//			System.out.println("push variable - for " + precedingToken.lexeme);
//			context.variableBuilder.pushId(precedingToken.lexeme);
//		}
	}

}
