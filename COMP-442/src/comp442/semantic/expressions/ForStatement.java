package comp442.semantic.expressions;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.codegen.instructions.BranchOnZeroInstruction;
import comp442.codegen.instructions.JumpInstruction;
import comp442.codegen.instructions.NoopInstruction;
import comp442.error.CompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.value.Value;

public class ForStatement extends ExpressionElement implements Statement {

	private AssignmentExpression initializer;
	private ExpressionElement condition;
	private AssignmentExpression increment;
	
	private StatementBlock statements;
	
	private static enum State {
		INITIALIZER,
		CONDITION,
		INCREMENT,
		STATEMENTS,
		DONE
	};
	
	private State state;
	
	public ForStatement() {
		state = State.INITIALIZER;
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		System.out.println("ForStatement accept sub element in state " + state);
		
		try{
		switch(state){
		case INITIALIZER:
			if(e instanceof AssignmentExpression){
				state = State.CONDITION;
				initializer = (AssignmentExpression) e;
			}else{
				super.acceptSubElement(e);
			}
			break;
		case CONDITION:
			if(e instanceof RelationExpressionFragment){
				state = State.INCREMENT;
				condition = e;
			}else{
				super.acceptSubElement(e);
			}
			break;
		case INCREMENT:
			if(e instanceof AssignmentExpression){
				state = State.STATEMENTS;
				increment = (AssignmentExpression) e;
			}else{
				super.acceptSubElement(e);
			}
			break;
		case STATEMENTS:
			if(e instanceof StatementBlock){
				state = State.DONE;
				statements = (StatementBlock) e;
				context.finishTopElement();
			}else{
				super.acceptSubElement(e);
			}
			break;
		default:
			super.acceptSubElement(e);
			break;
		}
		}catch(CompilerError x){
			System.err.println(x);
		}
	}
	
	@Override
	public void generateCode(CodeGenerationContext c) throws CompilerError {
		/*
		 * for loop

            **initializer
loop_top    **condition
            bz loop_end
            **loop_body
            **increment
            j loop_top
loop_end    nop
		 */

		int labelId = c.getUniqueLabelId();
		String loopTopLabel = "loop_top_" + labelId;
		String loopEndLabel = "loop_end_" + labelId;
		
		initializer.generateCode(c);
		
		c.labelNext(loopTopLabel);
		
		Register r = condition.getValue().getRegisterValue(c).getRegister();
		c.appendInstruction(new BranchOnZeroInstruction(r, loopEndLabel));
		if(!r.reserved) c.freeTemporaryRegister(r);
		
		statements.generateCode(c);
		
		increment.generateCode(c);
		
		c.appendInstruction(new JumpInstruction(loopTopLabel));
		c.appendInstruction(new NoopInstruction().setLabel(loopEndLabel));
	}

	@Override
	public String pseudoCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Value getValue() throws CompilerError {
		// TODO Auto-generated method stub
		return null;
	}

}
