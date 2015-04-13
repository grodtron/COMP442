package comp442.syntactical.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;

import comp442.codegen.CodeGenerationContext;
import comp442.codegen.Register;
import comp442.codegen.instructions.AddWordImmediateInstruction;
import comp442.codegen.instructions.AlignInstruction;
import comp442.codegen.instructions.EntryInstruction;
import comp442.codegen.instructions.HaltInstruction;
import comp442.error.CompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.symboltable.SymbolContext;
import comp442.semantic.symboltable.entries.FunctionEntry;

public class Driver {

	public static void main(String[] args) throws CompilerError, Exception {
		Properties properties = new Properties();
		
		String settingsFile = "settings.txt";
		
		try {
			properties.load(new FileInputStream(new File(settingsFile)));
		} catch (IOException e) {
			System.err.println("Could not load properties file '"+settingsFile+"' (" + e + ")");
			System.exit(1);
		}
		
		Parser p;
		
		try {
			p  = new Parser(new File(properties.getProperty("input")));
		} catch (FileNotFoundException e) {
			System.err.println("Could not load input file specified in '"+settingsFile+"' (" + e + ")");
			System.exit(1);
			return; // so compiler doesn't complain about uninitialized variable
		}
		
		p.parse();
		
		FunctionEntry program = (FunctionEntry) SymbolContext.find("program");
		
		CodeGenerationContext c = new CodeGenerationContext();
			
		String stackLabel = "stack";
		
		c.appendInstruction(new EntryInstruction());
		c.appendInstruction(new AddWordImmediateInstruction(Register.STACK_POINTER, Register.ZERO, stackLabel));
		
		for(Statement s : program.getStatements()){
			s.generateCode(c);
		}
		
		c.appendInstruction(new HaltInstruction());
		
		c.appendInstruction(new AlignInstruction().setLabel(stackLabel));
		c.printCode(new PrintStream(new File("output.m")));
		
		moonRun("output.m");
	}
	
	public static void moonRun(String filename) throws Exception{
		ProcessBuilder moon = new ProcessBuilder("Moon.exe", filename);
		
		Process moonProc = moon.start();
		
		moonProc.waitFor();
		
		BufferedReader r = new BufferedReader(new InputStreamReader(moonProc.getInputStream()));
		
		
		System.out.println("===== Moon Output =====");
		while(r.ready()){
			System.out.println(r.readLine());			
		}
		
	}

}
