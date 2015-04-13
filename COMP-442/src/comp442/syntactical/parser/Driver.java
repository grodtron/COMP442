package comp442.syntactical.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;

import comp442.codegen.CodeGenerator;
import comp442.error.CompilerError;

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
		
		CodeGenerator c = new CodeGenerator(new PrintStream("output.m"));
		c.generate();
		
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
