package comp442.codegen;

import java.util.HashMap;
import java.util.Map;

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
	
	EQUALS("==", "ceq", "ceqi", true){
		@Override
		public int operate(int a, int b) {
			return a == b ? SpecialValues.TRUE : SpecialValues.FALSE;
		}
	},
	
	NOT_EQUALS("<>", "cne", "cnei", true){
		@Override
		public int operate(int a, int b) {
			return a != b ? SpecialValues.TRUE : SpecialValues.FALSE;
		}
	},
	
	LESS_THAN("<", "clt", "clti", false){
		@Override
		public int operate(int a, int b) {
			return a < b ? SpecialValues.TRUE : SpecialValues.FALSE;
		}
	},
	
	LESS_THAN_EQUALS("<=", "cle", "clei", false){
		@Override
		public int operate(int a, int b) {
			return a <= b ? SpecialValues.TRUE : SpecialValues.FALSE;
		}
	},
	
	GREATER_THAN(">", "cgt", "cgti", false){
		@Override
		public int operate(int a, int b) {
			return a > b ? SpecialValues.TRUE : SpecialValues.FALSE;
		}
	},

	GREATER_THAN_EQUALS(">=", "cge", "cgei", false){
		@Override
		public int operate(int a, int b) {
			return a >= b ? SpecialValues.TRUE : SpecialValues.FALSE;
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
	
	private final static Map<String, MathOperation> operators;
	
	static {
		operators = new HashMap<String, MathOperation>(values().length);
		for(MathOperation op : values()){
			operators.put(op.symbol, op);
		}
	}
	
	public static MathOperation fromToken(String token) throws CompilerError{
		MathOperation op = operators.get(token);
		if(op != null){
			return op;
		}else{
			throw new CompilerError("Unknown addition operator '" + token + "'");
		}
	}
	
}
