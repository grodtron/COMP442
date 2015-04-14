package comp442.logging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Log {

	private static PrintStream error;
	private static PrintStream output;
	private static PrintStream tokens;
	private static PrintStream derivation;
	private static PrintStream symbols;
	private static PrintStream masm;
	private static File masmFile;
	
	static {
		error      = System.err;
		output     = System.out;
		derivation = System.out;
		tokens     = System.out;
		symbols    = System.out;
		
		masm       = System.out;
		masmFile   = new File("/dev/null");
	}

	public static void init(File input) throws FileNotFoundException{
		String baseName = input.getName();
		String folder   = input.getParent();
		baseName = baseName.substring(0, baseName.lastIndexOf('.'));
		
		new File(folder + "/generated/").mkdirs();
		
		Log.error      = new PrintStream(new File(folder + "/generated/" + baseName + ".error"));
		Log.output     = new PrintStream(new File(folder + "/generated/" + baseName + ".output"));
		Log.derivation = new PrintStream(new File(folder + "/generated/" + baseName + ".derivation"));
		Log.tokens     = new PrintStream(new File(folder + "/generated/" + baseName + ".tokens"));
		Log.symbols    = new PrintStream(new File(folder + "/generated/" + baseName + ".symbols"));
		
		Log.masmFile   = new File(folder + "/" + baseName + ".m");
		// The actual ASM file
		Log.masm       = new PrintStream(Log.masmFile);			
	}
	
	public static void close(){
		
		Log.error.close();
		Log.output.close();
		Log.derivation.close();
		Log.tokens.close();
		Log.symbols.close();
		Log.masm.close();
		
	}
	
	public static void logError(String string) {
		Log.error.println(string);
		if(Log.error != System.err){
			System.err.println(string);
		}
	}

	public static PrintStream getError() {
		return Log.error;
	}

	public static PrintStream getOutput() {
		return Log.output;
	}

	public static PrintStream getDerivation() {
		return Log.derivation;
	}

	public static PrintStream getTokens() {
		return Log.tokens;
	}

	public static PrintStream getSymbols() {
		return Log.symbols;
	}

	public static PrintStream getMasm() {
		return Log.masm;
	}

	public static File getMasmFile() {
		return Log.masmFile;
	}

	public static void closeMasm() {
		Log.masm.close();
	}
	
	
	
}
