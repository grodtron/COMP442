package comp442.semantic.expressions;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.codegen.instructions.BranchOnZeroInstruction;
import comp442.codegen.instructions.JumpInstruction;
import comp442.codegen.instructions.NoopInstruction;
import comp442.error.CompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.value.RegisterValue;
import comp442.semantic.value.Value;
import comp442.semantic.value.VoidValue;

public class IfStatement extends ExpressionElement implements Statement {

	private ExpressionElement condition;
	
	private StatementBlock thenBlock;
	private StatementBlock elseBlock;
	
	private static enum State {
		CONDITION,
		THEN_BLOCK,
		ELSE_BLOCK,
		DONE,
	};
	
	private State state;
	
	public IfStatement() {
		state = State.CONDITION;
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		switch(state){
			case CONDITION:
				if(e instanceof AdditionExpressionFragment) {// TODO - relExpr
					condition = e;
					state = State.THEN_BLOCK;
				}else{
					super.acceptSubElement(e);					
				}
				break;
			case THEN_BLOCK:
				if(e instanceof StatementBlock){
					thenBlock = (StatementBlock) e;
					state = State.ELSE_BLOCK;
				}else{
					super.acceptSubElement(e);
				}
				break;
			case ELSE_BLOCK:
				if(e instanceof StatementBlock){
					elseBlock = (StatementBlock) e;
					state = State.DONE;
					context.finishTopElement();
				}else{
					super.acceptSubElement(e);
				}
				break;
			case DONE:
			default:
				super.acceptSubElement(e);
				break;
		}
	}
	
	@Override
	public void generateCode(CodeGenerationContext c) throws CompilerError {
		RegisterValue conditionValue = condition.getValue().getRegisterValue(c);
		Register r = conditionValue.getRegister();
		int labelId = c.getUniqueLabelId();

		String elseLabel  = "else_"  + labelId;
		String endifLabel = "endif_" + labelId;
		
		c.appendInstruction(new BranchOnZeroInstruction(r, elseLabel));
		
		thenBlock.generateCode(c);
		
		c.appendInstruction(new JumpInstruction(endifLabel));
		c.labelNext(elseLabel);
		
		elseBlock.generateCode(c);
		
		c.appendInstruction(new NoopInstruction().setLabel(endifLabel));
		
	}

	@Override
	public String pseudoCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value getValue() throws CompilerError {
		return VoidValue.get();
	}

}
