package comp442.lexical;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import comp442.lexical.token.Token;

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
		
		Token t = null;
		
		do{
			try {
				t = s.getNext();
				if (!s.done()) output.println(t);
			} catch (InvalidCharacterException e) {
				System.err.println("Invalid character '" + e.character + "' encountered on line " + e.lineNumber);
				continue;
			} catch (IOException e) {
				System.err.println("Error while scanning input (" + e + ")");
				System.exit(1);
			}
		}while(! s.done());
		
		output.close();

	}

}
