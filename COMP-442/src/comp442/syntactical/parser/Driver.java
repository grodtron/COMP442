package comp442.syntactical.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import comp442.codegen.CodeGenerationContext;
import comp442.error.CompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.symboltable.SymbolContext;
import comp442.semantic.symboltable.entries.FunctionEntry;

public class Driver {

	public static void main(String[] args) throws CompilerError {
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
				
		for(Statement s : program.getStatements()){
			s.generateCode(c);
		}
		
		c.printCode(System.out);

	}

}
