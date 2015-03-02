package comp442.syntactical.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import comp442.lexical.Scanner;

public class Driver {

	public static void main(String[] args) {
		Properties properties = new Properties();
		
		String settingsFile = "settings.txt";
		
		try {
			properties.load(new FileInputStream(new File(settingsFile)));
		} catch (IOException e) {
			System.err.println("Could not load properties file '"+settingsFile+"' (" + e + ")");
			System.exit(1);
		}
		
		FileInputStream input;
		try {
			input = new FileInputStream(properties.getProperty("input"));
		} catch (FileNotFoundException e) {
			System.err.println("Could not load input file specified in '"+settingsFile+"' (" + e + ")");
			System.exit(1);
			return; // so compiler doesn't complain about uninitialized variable
		}
		
		PrintStream output;
		try {
			output = new PrintStream(new FileOutputStream(properties.getProperty("output")));
		} catch (FileNotFoundException e) {
			System.err.println("Could not open output file specified in '"+settingsFile+"' (" + e + ")");
			System.exit(1);
			try { input.close(); } catch (IOException e1) { }
			return; // so compiler doesn't complain about uninitialized variable
		}
		
		Scanner s = new Scanner(input);

		Parser p  = new Parser(s);
		
		p.parse();
		
		output.close();

	}

}
