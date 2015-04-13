package comp442.semantic.value;

import java.util.ArrayList;
import java.util.List;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.codegen.instructions.AddWordImmediateInstruction;
import comp442.codegen.instructions.JumpAndLinkInstruction;
import comp442.codegen.instructions.StoreWordInstruction;
import comp442.error.CompilerError;
import comp442.semantic.expressions.AdditionExpressionFragment;
import comp442.semantic.symboltable.entries.FunctionEntry;
import comp442.semantic.symboltable.entries.types.FunctionType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class FunctionCallValue extends DynamicValue implements Value {

	private List<Value> arguments;
	private int scopeSize;
	
	private String callingLabel;
	
	public FunctionCallValue(FunctionEntry entry, List<AdditionExpressionFragment> expressions) throws CompilerError {
		int nArgs = expressions.size();
		List<SymbolTableEntryType> argTypes = ((FunctionType)entry.getType()).getArgumentTypes();
		
		if(nArgs != argTypes.size()){
			throw new CompilerError("wrong number of arguments for function " + entry.getName() + ", expected " + argTypes.size() + ", got " + nArgs);
		}
		
		arguments = new ArrayList<Value>(expressions.size());
		
		for(AdditionExpressionFragment exp : expressions){
			Value arg = exp.getValue();
			// TODO - type checking!!!
			arguments.add(arg);
		}
		
		callingLabel = entry.getLabel();
		scopeSize = entry.getScope().getSize();
	}

	@Override
	public Value getUseableValue(CodeGenerationContext c) throws CompilerError {
		// TODO Auto-generated method stub
		
		// Pass the parameters
		int offset = 0;
		for(Value arg : arguments){
			c.appendInstruction(new StoreWordInstruction(Register.STACK_POINTER, offset, arg.getRegisterValue(c).getRegister()));
			offset += 4;
		}
		
		c.appendInstruction(new AddWordImmediateInstruction(Register.STACK_POINTER, Register.STACK_POINTER, scopeSize));
		c.appendInstruction(new JumpAndLinkInstruction(Register.RETURN_ADDRESS, callingLabel));
		
		return new RegisterValue(Register.RETURN_VALUE);
	}

	@Override
	public RegisterValue getRegisterValue(CodeGenerationContext c) throws CompilerError {
		return getUseableValue(c).getRegisterValue(c);
	}

}
