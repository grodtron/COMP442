package comp442.semantic.value;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.MathOperation;
import comp442.codegen.Register;
import comp442.codegen.instructions.ImmediateMathOperationInstruction;
import comp442.codegen.instructions.MathOperationInstruction;
import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;


public class MathValue extends DynamicValue {

	private final MathOperation operator;
	private final Value b;
	private final Value a;
	
	public MathValue(MathOperation operator, Value a, Value b) {
		this.operator = operator;
		this.a = a;
		this.b = b;
		
		System.out.println("New MathValue: " + this);
	}

	@Override
	public Value getUseableValue(CodeGenerationContext c) throws CompilerError {
		Value uA = a.getUseableValue(c);
		Value uB = b.getUseableValue(c);
		
		if(uA instanceof RegisterValue && uB instanceof RegisterValue){
			return _getUseableValue((RegisterValue)uA, (RegisterValue)uB, c);
		}
		
		if(uA instanceof StaticValue && uB instanceof RegisterValue){
			return _getUseableValue((StaticValue)uA, (RegisterValue)uB, c);
		}

		if(uA instanceof RegisterValue && uB instanceof StaticValue){
			return _getUseableValue((RegisterValue)uA, (StaticValue)uB, c);
		}

		if(uA instanceof StaticValue && uB instanceof StaticValue){
			return _getUseableValue((StaticValue)uA, (StaticValue)uB, c);
		}
		
		
		throw new InternalCompilerError("Unexpected combination of types for a and b: " + uA.getClass() + ", " + uB.getClass());
	}

	private Value _getUseableValue(StaticValue uA, StaticValue uB, CodeGenerationContext c) {
		// If both values are static, then we do the operation at compile time! We're so smart!
		return new StaticIntValue(operator.operate(uA.intValue(), uB.intValue()));
	}

	private Value _getUseableValue(RegisterValue uA, StaticValue uB, CodeGenerationContext c) {
		Register aReg = uA.getRegister();
		Register temp;
		if(aReg.reserved){
			temp = c.getTemporaryRegister();
		}else{
			temp = aReg;
		}
		
		c.appendInstruction(new ImmediateMathOperationInstruction(operator.immediateOpcode, temp, aReg, uB.intValue()));

		return new RegisterValue(temp);
	}

	private Value _getUseableValue(StaticValue uA, RegisterValue uB, CodeGenerationContext c) throws CompilerError {
		if(operator.commutative){
			return _getUseableValue(uB, uA, c);
		}else{
			return _getUseableValue(uA.getRegisterValue(c), uB, c);
		}
	}

	private Value _getUseableValue(RegisterValue uA, RegisterValue uB, CodeGenerationContext c) throws CompilerError {
		Register aReg = uA.getRegister();
		Register bReg = uB.getRegister();
		Register temp;
		boolean free = false;
		if(aReg.reserved){
			if(bReg.reserved){
				temp = c.getTemporaryRegister();
			}else{
				temp = bReg;
			}
		}else{
			temp = aReg;
			if(! bReg.reserved){
				free = true;
			}
		}
		
		c.appendInstruction(new MathOperationInstruction(operator.opcode, temp, aReg, bReg));
		
		if(free){
			c.freeTemporaryRegister(bReg);
		}
		
		return new RegisterValue(temp);
	}

	@Override
	public RegisterValue getRegisterValue(CodeGenerationContext c) throws CompilerError {
		return getUseableValue(c).getRegisterValue(c);
	}
	
	@Override
	public String toString() {
		return "(" + a + " " + operator.symbol + " " + b + ")";
	}
}
