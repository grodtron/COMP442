package comp442;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import comp442.codegen.CodeGenerator;
import comp442.logging.Log;
import comp442.syntactical.parser.Parser;

public class Compiler {

	
	public static void compileAndRun(String inputFileName) throws IOException {

		File f = new File(inputFileName);
		
		System.out.println("~================< Source Code file >================~");
		{
			BufferedReader reader = new BufferedReader(new FileReader(f));
			
			while(reader.ready()){
				System.out.println(reader.readLine());
			}
			
			reader.close();
		}
		
		Log.init(f);
		
		System.out.println("~================< Compiler Output  >================~");
		
		Parser parser = new Parser(f);
	
		parser.parse();
		
		if(parser.getNErrors() > 0){
			Log.logError("Errors during parsing first pass, aborting");
			System.exit(1);
		}
		
		CodeGenerator codeGenerator = new CodeGenerator(Log.getMasm());
				
		codeGenerator.generate();
		
		if(codeGenerator.getNumErrors() > 0){
			Log.logError("Errors during parsing second pass, aborting");
			System.exit(2);
		}
		
		Log.closeMasm();
		
		System.out.println("~================<    Moon Output   >================~");
		
		moonRun(Log.getMasmFile());
		
		Log.close();
	}
	
	public static void moonRun(File f) throws IOException{

		ProcessBuilder moon = new ProcessBuilder("./moon", f.getPath());
		
		Process moonProc = moon.start();
		
		try {
			moonProc.waitFor();
		} catch (InterruptedException e) {
			Log.logError("Moon process interrupted");;
		}
		
		BufferedReader r = new BufferedReader(new InputStreamReader(moonProc.getInputStream()));
		
		while(r.ready()){
			System.out.println(r.readLine());			
		}
		
	}

	
	public static void main(String[] args) {
				
		String inputFileName;
		
		if(args.length > 0){
			inputFileName = args[0];
		}else{
			try {
				Properties properties = new Properties();
				String settingsFile = "settings.txt";
				properties.load(new FileInputStream(new File(settingsFile)));
				inputFileName = properties.getProperty("input");
			} catch (IOException e) {
				System.err.println("An IOException occured while looking for settings file: " + e.getMessage());
				System.exit(1);
				return; // unused variable
			}
		}
		
		
		try {
			compileAndRun(inputFileName);
		} catch (IOException e) {
			Log.logError("An IO error occured: " + e.getMessage());
		}	
	}	
}
