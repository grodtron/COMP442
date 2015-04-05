package comp442.lexical;

import java.io.IOException;
import java.io.InputStream;

import comp442.lexical.state.Start;
import comp442.lexical.state.State;
import comp442.lexical.token.Token;

public class Scanner {

	private final LexableInputStream input;
	
	private State state;
	
	/**
	 * Create a new Scanner to operate on the given input stream.
	 * 
	 * The scanner can be used to extract tokens one by one based
	 * on the text file contained in the InputStream.
	 * 
	 * @param input The InputStream containing the string to process.
	 * @throws IOException If input does not support marks (input.markSupported() => false).
	 */
	public Scanner(InputStream input) {
		// the increased pushback buffer is only needed for reserved words,
		// where "if" and "int", "float" and "for", etc. need to read then unread
		// 2 chars to differentiate from each other
		this.input = new LexableInputStream(input, 8);
		this.state = new Start();
	}
	
	public Token getNext() {
		
		do {
			try {
				state = state.process(input);
			} catch (InvalidCharacterException e) {
				System.err.println("Invalid character '" + e.character + "' encountered on line " + e.lineNumber);
				continue;
			} catch (IOException e) {
				System.err.println("Error while scanning input (" + e + ")");
				System.exit(1);
			}

		}while(! state.nextTokenReady() && ! state.done() );
		
		return state.getToken();
	}
	
	public boolean done(){
		return state.done();
	}
}
