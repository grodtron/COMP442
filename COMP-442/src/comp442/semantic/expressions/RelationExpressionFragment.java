package comp442.semantic.expressions;

import comp442.codegen.MathOperation;
import comp442.error.CompilerError;
import comp442.semantic.value.MathValue;
import comp442.semantic.value.Value;

public class RelationExpressionFragment extends ExpressionElement {

	private static enum State {
		WAITING_FOR_FIRST,
		WAITING_FOR_OP,
		WAITING_FOR_SECOND,
		DONE
	};

	private State state;
	private ExpressionElement first;
	private ExpressionElement second;
	private MathOperation operator;

	public RelationExpressionFragment(){
		state = State.WAITING_FOR_FIRST;
	}
	
	@Override
	public void pushRelationOperator(MathOperation operator) throws CompilerError {
		if(this.state == State.WAITING_FOR_OP ){
			this.operator = operator;
			this.state = State.WAITING_FOR_SECOND;
		}
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		
		if(e instanceof RelationExpressionFragment
		|| e instanceof AdditionExpressionFragment){
			switch(state){
			case WAITING_FOR_FIRST:
				first = e;
				state = State.WAITING_FOR_OP;
				break;
			case WAITING_FOR_SECOND:
				second = e;
				state = State.DONE;
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
	public Value getValue() throws CompilerError {
		if(state == State.WAITING_FOR_OP){
			return first.getValue();
		}else{
			return new MathValue(operator, first.getValue(), second.getValue());			
		}
	}
	

}
