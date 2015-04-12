package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.codegen.instructions.LoadWordInstruction;
import comp442.error.InternalCompilerError;

public class ConcreteAddressValue extends DynamicValue {

	private RegisterValue baseAddr;
	private StaticValue   offset;
	
	public ConcreteAddressValue(RegisterValue baseAddr, StaticValue offset) {
		this.baseAddr = baseAddr;
		this.offset   = offset;
	}

	public int getOffset(){
		return offset.intValue();
	}
	
	public Register getBaseAddress(){
		return baseAddr.getRegister();
	}
	
	@Override
	public Value getUseableValue(CodeGenerationContext c) throws InternalCompilerError {
		RegisterValue tempReg;
		if(baseAddr.getRegister().reserved){
			tempReg = new RegisterValue(c.getTemporaryRegister());
		}else{
			tempReg = baseAddr;
		}
		
		c.appendInstruction(new LoadWordInstruction(tempReg, baseAddr, offset));
		
		return tempReg;
	}

	@Override
	public RegisterValue getRegisterValue(CodeGenerationContext c) throws InternalCompilerError {
		return (RegisterValue) getUseableValue(c);
	}

}
