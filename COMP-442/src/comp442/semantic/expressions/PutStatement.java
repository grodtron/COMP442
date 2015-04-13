package comp442.semantic.expressions;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.codegen.instructions.PutInstruction;
import comp442.error.CompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.value.RegisterValue;
import comp442.semantic.value.Value;
import comp442.semantic.value.VoidValue;

public class PutStatement extends ExpressionElement implements Statement {

	RelationExpressionFragment expr;
	
	@Override
	public void generateCode(CodeGenerationContext c) throws CompilerError {
		RegisterValue useableVal = expr.getValue().getRegisterValue(c);
		
		Register r = useableVal.getRegister();
		
//		c.appendInstruction(new ShiftLeftInstruction(r, r, 24))
		c.appendInstruction(new PutInstruction(r));
	
		
		c.freeTemporaryRegister(r);
	}

	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		if(e instanceof RelationExpressionFragment){
			expr = (RelationExpressionFragment) e;
			context.finishTopElement();
		}else{
			super.acceptSubElement(e);			
		}
	}
	
	@Override
	public String pseudoCode() {
		return "put " + expr;
	}

	@Override
	public Value getValue() throws CompilerError {
		return VoidValue.get();
	}

}
