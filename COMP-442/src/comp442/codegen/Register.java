package comp442.codegen;

public enum Register {
	ZERO("R0"),
	STACK_POINTER("R1"),
	R2,
	R3,
	R4,
	R5,
	R6,
	R7,
	R8,
	R9,
	R10,
	R11,
	R12,
	R13,
	R14,
	R15,
	PC,
	;
	
	public final String registerName;
	public final String symbolicName;
	
	private Register(String name){
		registerName = name;
		symbolicName = name + "(" + toString() + ")";
	}
	
	private Register(){
		registerName = this.toString();
		symbolicName = registerName;
	}
	
}
