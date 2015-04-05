package comp442.lexical;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class LexableInputStream extends PushbackInputStream {

	private int lineNumber;
	private int columnNumber;
	
	public LexableInputStream(InputStream in) {
		this(in, 1);
	}
	
	public LexableInputStream(InputStream in, int size) {
		super(in, size);
		
		// start on line 1
		lineNumber = 1;
	}

	@Override
	public int read() throws IOException {
		int value = super.read();
		
		if(value > 127){
			throw new InvalidCharacterException(value, lineNumber);
		}
		
		if(value == '\n'){
			++lineNumber;
			columnNumber = 0;
		}else{
			++columnNumber;
		}
		
		return value;
	}
	
	@Override
	public void unread(int value) throws IOException{
		// avoid unreading a -1 (EOF) this just makes our lives easier
		// everywhere else cause we don't have to treat it as a special case
		if(value >= 0){		
			if(value == '\n'){
				--lineNumber;
				// TODO FIXME - column count will bug with unread
				//
				// quick fix is to keep track of the previous row length
				// real fix is to keep a stack of all row lengths
			}
			
			super.unread(value);
		}
	}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

}
