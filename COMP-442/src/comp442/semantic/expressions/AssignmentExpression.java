package comp442.semantic.expressions;

import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.value.Value;

public class AssignmentExpression extends ExpressionElement implements Statement {

	private static enum State {
		INIT_LHS,
		LHS,
		INIT_RHS,
		RHS,
	}
	
	private State currentState;
	private Value rhs;
	private Value lhs;
	
	public AssignmentExpression(){
		currentState = State.INIT_LHS;
	}
	
	@Override
	public void pushIdentifier(String id) throws CompilerError {
		if(currentState == State.INIT_LHS){
			System.err.println(hashCode() + " " +"moving from INIT_LHS to LHS with ID " + id);
			currentState = State.LHS;
			context.pushChild(new VariableExpressionFragment(id));
		}else
		if(currentState == State.INIT_RHS){
			System.err.println(hashCode() + " " +"moving from INIT_RHS to RHS with ID " + id);
			currentState = State.RHS;
			context.pushChild(new VariableExpressionFragment(id));
		}else{
			super.pushIdentifier(id);
		}
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		if(currentState == State.LHS){
			lhs = e.getValue();
			System.err.println(hashCode() + " " +"moving from LHS to INIT_RHS with");
			currentState = State.INIT_RHS;
		}else
		if(currentState == State.RHS){
			rhs = e.getValue();
			System.err.println(hashCode() + " " +"completing RHS, finishing top element (self): " + pseudoCode());
			context.finishTopElement();
		}else{
			throw new InternalCompilerError("Unexpected " + e + " while in state " + currentState.toString());
		}		
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{ " + pseudoCode() + " }";
	}
	
	@Override
	public String pseudoCode() {
		return lhs + " = " + rhs;
	}

	public Value getLhs() {
		return lhs;
	}
	
	public Value getRhs() {
		return rhs;
	}
}
