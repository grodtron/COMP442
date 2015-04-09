package comp442.semantic.expressions;

import java.util.Stack;

import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;

public class ExpressionContext {

	/*package*/ static ExpressionContext instance = new ExpressionContext();

	private final Stack<ExpressionElement> expressionStack;

	
	private ExpressionContext(){
		expressionStack = new Stack<ExpressionElement>();
	}
	
	
	public void pushChild(ExpressionElement child){
		expressionStack.push(child);
	}
	
	// TODO better name
	void finishTopElement() throws CompilerError {
		ExpressionElement child = expressionStack.pop();
		ExpressionElement top   = expressionStack.peek();
		
		if(child == null){
			throw new InternalCompilerError("tried to pop with empty stack");
		}
		if( top  == null){
			// popped last element (TODO)
		}else{
			top.acceptSubElement(child);
		}
		
	}

	public ExpressionElement getCurrent() throws InternalCompilerError {
		if(expressionStack.isEmpty()){
			return null;
		}else{
			return expressionStack.peek();
		}
	}


	public void popChild() {
		expressionStack.pop();
	}
	
}
