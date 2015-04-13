package comp442.semantic.expressions;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.codegen.instructions.GetInstruction;
import comp442.codegen.instructions.StoreWordInstruction;
import comp442.error.CompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.value.ConcreteAddressValue;
import comp442.semantic.value.StoredValue;
import comp442.semantic.value.Value;
import comp442.semantic.value.VoidValue;

public class GetStatement extends ExpressionElement implements Statement {

	StoredValue destination;
	
	@Override
	public void generateCode(CodeGenerationContext c) throws CompilerError {
		Register r = c.getTemporaryRegister();
		
		ConcreteAddressValue addr = destination.getConcreteAddress(c);
		
		c.appendInstruction(new GetInstruction(r));
		
		c.appendInstruction(new StoreWordInstruction(addr.getBaseAddress(), addr.getOffset(), r));
		
		c.freeTemporaryRegister(r);
		c.freeTemporaryRegister(addr.getBaseAddress());
	}

	@Override
	public void pushIdentifier(String id) throws CompilerError {
		context.pushChild(new VariableExpressionFragment(id));
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		if(e instanceof VariableExpressionFragment){
			destination = (StoredValue)e.getValue();
			context.finishTopElement();
		}else{
			super.acceptSubElement(e);
		}
	}
	
	@Override
	public String pseudoCode() {
		return "get " + destination;
	}

	@Override
	public Value getValue() throws CompilerError {
		return VoidValue.get();
	}

}
