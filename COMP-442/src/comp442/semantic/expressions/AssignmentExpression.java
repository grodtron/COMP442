package comp442.semantic.expressions;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.codegen.instructions.StoreWordInstruction;
import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.value.ConcreteAddressValue;
import comp442.semantic.value.RegisterValue;
import comp442.semantic.value.StoredValue;
import comp442.semantic.value.Value;
import comp442.semantic.value.VoidValue;

public class AssignmentExpression extends ExpressionElement implements Statement {

	private static enum State {
		INIT_LHS,
		LHS,
		INIT_RHS,
		RHS,
		DONE
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
			System.err.println(hashCode() + " " +"moving from LHS to RHS with");
			currentState = State.RHS;
		}else
		if(currentState == State.RHS){
			rhs = e.getValue();
			System.err.println(hashCode() + " " +"completing RHS, finishing top element (self): " + pseudoCode());
			currentState = State.DONE;
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

	@Override
	public Value getValue() {
		return VoidValue.get();
	}
	
	public Value getLhs() {
		return lhs;
	}
	
	public Value getRhs() {
		return rhs;
	}
	
	@Override
	public void generateCode(CodeGenerationContext c) throws CompilerError {
		if(currentState == State.DONE){
			
			if(lhs instanceof StoredValue){
				RegisterValue    rhsRegisterValue = rhs.getRegisterValue(c);
				ConcreteAddressValue lhsAddrValue = ((StoredValue)lhs).getConcreteAddress(c);
				
				c.appendInstruction(new StoreWordInstruction(lhsAddrValue.getBaseAddress(), lhsAddrValue.getOffset(), rhsRegisterValue.getRegister()));
				
				Register rhsReg = rhsRegisterValue.getRegister();
				if( ! rhsReg.reserved){
					c.freeTemporaryRegister(rhsReg);
				}
				
				Register lhsReg = lhsAddrValue.getBaseAddress();
				if( ! lhsReg.reserved){
					c.freeTemporaryRegister(lhsReg);
				}
			}else{
				throw new InternalCompilerError("Expected LHS to be a StoredValue, instead got " + lhs.getClass().getName());
			}
		}else{
			throw new InternalCompilerError("Tried to generate code from incomplete " + AssignmentExpression.class.getSimpleName() + " statement");
		}
	}
}
