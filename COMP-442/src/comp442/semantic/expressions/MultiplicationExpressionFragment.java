package comp442.semantic.expressions;

import comp442.codegen.MathOperation;
import comp442.error.CompilerError;
import comp442.semantic.value.MathValue;
import comp442.semantic.value.Value;

public class MultiplicationExpressionFragment extends ExpressionElement {

	private static enum State {
		INIT_FIRST,
		FIRST,
		WAITING_FOR_OP,
		INIT_SECOND,
		SECOND,
		DONE
	};
	
	private State state;
	private ExpressionElement first;
	private ExpressionElement second;
	private MathOperation operator;
	
	public MultiplicationExpressionFragment() {
		state = State.INIT_FIRST;
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		
		if(e instanceof MultiplicationExpressionFragment
		|| e instanceof VariableExpressionFragment
		|| e instanceof AdditionExpressionFragment){
			
			switch(state){
			case INIT_FIRST:
			case FIRST:
				state = State.WAITING_FOR_OP;
				first = e;
				break;
			case INIT_SECOND:
			case SECOND:
				state = State.DONE;
				second = e;
				context.finishTopElement();
				break;
			default:
				super.acceptSubElement(e);
				break;
			
			}	
		}else{
			super.acceptSubElement(e);
		}	
	}
	
	@Override
	public void pushIdentifier(String id) throws CompilerError {
		switch(state){
		case INIT_FIRST:
			state = State.FIRST;
			context.pushChild(new VariableExpressionFragment(id));
			break;
		case INIT_SECOND:
			state = State.SECOND;
			context.pushChild(new VariableExpressionFragment(id));
			break;
		default:
			super.pushIdentifier(id);
			break;		
		}
	}
	
	@Override
	public void pushIntLiteral(int i) throws CompilerError {
		switch(state){
		case INIT_FIRST:
			state = State.WAITING_FOR_OP;
			first = new IntLiteralExpressionElement(i);
			break;
		case INIT_SECOND:
			state = State.DONE;
			second = new IntLiteralExpressionElement(i);
			context.finishTopElement();
			break;
		default:
			super.pushIntLiteral(i);
			break;		
		}

	}
	
	@Override
	public void pushMultiplicationOperator(MathOperation operator) throws CompilerError {
		if(state == State.WAITING_FOR_OP){
			state = State.INIT_SECOND;
			this.operator = operator;
		}else{
			super.pushMultiplicationOperator(operator);
		}
	}
	
	@Override
	public Value getValue() throws CompilerError {
		if(state == State.WAITING_FOR_OP){
			return first.getValue();
		}else{
			return new MathValue(operator, first.getValue(), second.getValue());
		}
	}

}
