package comp442.semantic.expressions;

import comp442.codegen.MathOperation;
import comp442.error.CompilerError;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;
import comp442.semantic.value.MathValue;
import comp442.semantic.value.Value;

public class MultiplicationExpressionFragment extends TypedExpressionElement {

	private static enum State {
		INIT_FIRST,
		FIRST,
		WAITING_FOR_OP,
		INIT_SECOND,
		SECOND,
		DONE
	};
	
	private State state;
	private TypedExpressionElement first;
	private TypedExpressionElement second;
	private MathOperation operator;
	
	public MultiplicationExpressionFragment() {
		state = State.INIT_FIRST;
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		
		if(e instanceof MultiplicationExpressionFragment
		|| e instanceof VariableExpressionFragment
		|| e instanceof AdditionExpressionFragment
		|| e instanceof FunctionCallExpressionFragment){
			
			switch(state){
			case INIT_FIRST:
			case FIRST:
				state = State.WAITING_FOR_OP;
				first = (TypedExpressionElement) e;
				break;
			case INIT_SECOND:
			case SECOND:
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

	@Override
	public SymbolTableEntryType getType() {
		return first.getType();
	}

}
