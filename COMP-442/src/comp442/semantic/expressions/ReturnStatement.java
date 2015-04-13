package comp442.semantic.expressions;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.codegen.instructions.AddWordImmediateInstruction;
import comp442.codegen.instructions.AddWordInstruction;
import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.value.RegisterValue;
import comp442.semantic.value.StaticValue;
import comp442.semantic.value.Value;
import comp442.semantic.value.VoidValue;

public class ReturnStatement extends ExpressionElement implements Statement {

	Value returnValue;
	
	@Override
	public void generateCode(CodeGenerationContext c) throws CompilerError {
		Value useableReturnVal = returnValue.getUseableValue(c);
		if(useableReturnVal instanceof RegisterValue){
			c.appendInstruction(new AddWordInstruction(Register.RETURN_VALUE, Register.ZERO, ((RegisterValue) useableReturnVal).getRegister()));
		}else
		if(useableReturnVal instanceof StaticValue){
			c.appendInstruction(new AddWordImmediateInstruction(Register.RETURN_VALUE, Register.ZERO, ((StaticValue) useableReturnVal).intValue()));
		}else{
			throw new InternalCompilerError("Useable Value retured an unexpected type " + useableReturnVal.getClass());
		}
	}

	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		if(e instanceof RelationExpressionFragment){
			returnValue = e.getValue();
			context.finishTopElement();
		}else{
			super.acceptSubElement(e);			
		}
	}
	
	@Override
	public String pseudoCode() {
		return "return " + returnValue;
	}

	@Override
	public Value getValue() throws CompilerError {
		return VoidValue.get();
	}

}
