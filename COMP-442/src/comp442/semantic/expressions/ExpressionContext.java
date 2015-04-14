package comp442.semantic.expressions;

import java.util.Stack;

import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.symboltable.entries.FunctionEntry;

public class ExpressionContext {

	/*package*/ static ExpressionContext instance = new ExpressionContext();

	private final Stack<ExpressionElement> expressionStack;

	private FunctionEntry currentFunction;
	
	private ExpressionContext(){
		expressionStack = new Stack<ExpressionElement>();
	}
	
	
	public void pushChild(ExpressionElement child){
		expressionStack.push(child);
	}
	
	// TODO better name
	public void finishTopElement() throws CompilerError {
		ExpressionElement child;
		
		if(expressionStack.isEmpty()){
			throw new InternalCompilerError("tried to pop with empty stack");
		}else{
			child = expressionStack.pop();
		}
		
		if( expressionStack.isEmpty() ){
			if(child instanceof Statement){
				currentFunction.appendStatement((Statement)child);
			}else{
				// TODO - this is not an error, more a warning throw new InternalCompilerError("Emptied stack with non-" + Statement.class.getSimpleName() + " element: " + child);
			}
		}else{
			expressionStack.peek().acceptSubElement(child);
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


	public static void setCurrentFunction(FunctionEntry storedFunction) {
		instance.currentFunction = storedFunction;
	}
	
}
