package comp442.codegen;

import comp442.error.CompilerError;

public enum MathOperation {

	ADD("+", "add", "addi", true){
		@Override
		public int operate(int a, int b){
			return a + b;
		}
	},
	SUBTRACT("-", "sub", "subi", false){
		@Override
		public int operate(int a, int b){
			return a - b;
		}
	},
	OR("or", "or", "ori", true){
		@Override
		public int operate(int a, int b){
			return a | b;
		}
	},

	MULTIPLY("*", "mul", "muli", true){
		@Override
		public int operate(int a, int b){
			return a * b;
		}
	},
	DIVIDE("/", "div", "divi", false){
		@Override
		public int operate(int a, int b){
			return a / b;
		}
	},
	AND("and", "and", "andi", true){
		@Override
		public int operate(int a, int b){
			return a & b;
		}
	},

	;
	
	public final String symbol;
	public final boolean commutative;
	public final String opcode;
	public final String immediateOpcode;

	public abstract int operate(int a, int b);
	
	private MathOperation(String token, String opcode, String immediateOpcode, boolean commutative){
		this.symbol = token;
		this.commutative = commutative;
		this.opcode = opcode;
		this.immediateOpcode = immediateOpcode;
	}
	
	public static MathOperation fromToken(String token) throws CompilerError{
		for(MathOperation op : values()){
			if(op.symbol.equals(token)){
				return op;
			}
		}
		throw new CompilerError("Unknown addition operator '" + token + "'");
	}
	
}
