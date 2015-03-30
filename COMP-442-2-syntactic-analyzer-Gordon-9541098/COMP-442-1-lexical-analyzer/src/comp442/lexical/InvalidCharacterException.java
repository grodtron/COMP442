package comp442.lexical;

import java.io.IOException;

public class InvalidCharacterException extends IOException {

	public final char character;
	
	public final int lineNumber;
	
	public InvalidCharacterException(int c, int lineNumber) {
		this.character  = (char)c;
		this.lineNumber = lineNumber;
	}

	private static final long serialVersionUID = 5733231086605441596L;

}
