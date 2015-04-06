package comp442.logging;

import java.io.PrintStream;

public class Log {

	public static PrintStream error;
	public static PrintStream output;
	public static PrintStream derivation;
	public static PrintStream symbols;
	public static PrintStream masm;
	
	static {
		error      = System.err;
		output     = System.out;
		derivation = System.out;
		symbols    = System.out;
		
		masm       = System.out;
	}
	
	
	
}
