package comp442.semantic.expressions;

import comp442.codegen.MathOperation;
import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.semantic.value.Value;

public abstract class ExpressionElement {

	protected final ExpressionContext context = ExpressionContext.instance;
	
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		throw new InternalCompilerError("Unexpected " + e.getClass().getSimpleName() + " inside " + this.getClass().getSimpleName());
	}

	public void pushIdentifier(String id) throws CompilerError {
		throw new InternalCompilerError("Unexpected identifier " + id + " inside " + this.getClass().getSimpleName());
	}
	
	public abstract Value getValue() throws CompilerError;

	public void pushIntLiteral(int i) throws CompilerError {
		throw new InternalCompilerError("Unexpected int literal " + i + " inside " + this.getClass().getSimpleName());		
	}

	public void pushFloatLiteral(float f) throws CompilerError {
		throw new InternalCompilerError("Unexpected float literal " + f + " inside " + this.getClass().getSimpleName());		
	}

	public void pushAdditionOperator(MathOperation operator) throws CompilerError {
		throw new InternalCompilerError("Unexpected addition operation " + operator + " inside " + this.getClass().getSimpleName());				
	}

	public void pushMultiplicationOperator(MathOperation operator) throws CompilerError {
		throw new InternalCompilerError("Unexpected multiplication operation " + operator + " inside " + this.getClass().getSimpleName());				
	}

	public void pushRelationOperator(MathOperation operator) throws CompilerError {
		throw new InternalCompilerError("Unexpected relation operation " + operator + " inside " + this.getClass().getSimpleName());				
	}
	
}
