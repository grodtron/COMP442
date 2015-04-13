package comp442.codegen;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum Register {
	ZERO("R0"),
	STACK_POINTER("R1"),
	RETURN_ADDRESS("R2"),
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
	PROGRAM_COUNTER("PC"),
	;
	
	public final static Register RETURN_VALUE = RETURN_ADDRESS;
	
	public final String registerName;
	public final String symbolicName;
	public final boolean reserved;

	
	public final static Set<Register> unallocatedRegisters;
	
	static {
		Set<Register> _unallocatedRegisters = new HashSet<Register>(16);
		
		for(Register r : values()){
			if(! r.reserved){
				_unallocatedRegisters.add(r);
			}
		}
		
		unallocatedRegisters = Collections.unmodifiableSet(_unallocatedRegisters);
	}
	
	private Register(String name){
		registerName = name;
		symbolicName = name + "(" + toString() + ")";
		reserved = true;
	}
	
	private Register(){
		registerName = this.toString();
		symbolicName = registerName;
		reserved = false;
	}
	
}
