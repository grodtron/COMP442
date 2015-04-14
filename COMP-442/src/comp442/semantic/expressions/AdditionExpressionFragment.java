package comp442.semantic.expressions;

import comp442.codegen.MathOperation;
import comp442.error.CompilerError;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;
import comp442.semantic.value.MathValue;
import comp442.semantic.value.Value;

public class AdditionExpressionFragment extends TypedExpressionElement {

	private static enum State {
		WAITING_FOR_FIRST,
		WAITING_FOR_OP,
		WAITING_FOR_SECOND,
		DONE
	};
	
	private State state;
	private TypedExpressionElement first;
	private TypedExpressionElement second;
	private MathOperation operator;
	
	public AdditionExpressionFragment() {
		state = State.WAITING_FOR_FIRST;
	}
	
	@Override
	public void pushAdditionOperator(MathOperation operator) throws CompilerError {
		if(this.state == State.WAITING_FOR_OP ){
			this.operator = operator;
			this.state = State.WAITING_FOR_SECOND;
		}
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		
		if(e instanceof MultiplicationExpressionFragment
		|| e instanceof AdditionExpressionFragment){
			switch(state){
			case WAITING_FOR_FIRST:
				first = (TypedExpressionElement) e;
				state = State.WAITING_FOR_OP;
				break;
			case WAITING_FOR_SECOND:
				second = (TypedExpressionElement) e;
				
				SymbolTableEntryType firstType  = first.getType();
				SymbolTableEntryType secondType = second.getType();
				
				if( ! firstType.equals(secondType) ){
					// TODO - late binding types!! // throw new CompilerError("Type mismatch: " + firstType + " is not compatible with " + secondType + " for operator '" + operator.symbol + "'");	
				}
				
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

	@Override
	public SymbolTableEntryType getType() {
		return first.getType();
	}
	
}
