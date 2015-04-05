package comp442.lexical.utils;

import java.io.IOException;
import java.io.PushbackInputStream;

public class Matcher {

	// static class yo
	private Matcher(){}
	
	/**
	 * Check if the next character sequence represents a reserved word.
	 * 
	 * If the next characters make up the given reserved word, then consume them
	 * from the stream and return true. Otherwise do not consume them and return
	 * false.
	 * 
	 * Considered to 
	 * 
	 * @param input
	 * @param target
	 * @return
	 * @throws IOException
	 */
	public static boolean matchesReservedWord(PushbackInputStream input, String target) throws IOException{
		byte [] targetString = target.getBytes();
		byte [] realString   = new byte[targetString.length];
		
		// If at any point we don't match the current string, roll back
		// and return false.
		for(int i = 0; i < targetString.length; ++i){
			realString[i] = (byte) input.read();
			
			// If they don't match, then push back everything read so far,
			// and return false
			if(realString[i] != targetString[i]){
				unread(input, realString, i);
				return false;
			}
		}
				
		byte c = (byte)input.read();
		if(Character.isLetterOrDigit(c) || c == '_'){
			// If the next character would continue an ID, then this is not
			// a proper reserved word. Unread everything
			input.unread(c);
			unread(input, realString, realString.length-1);
			return false;			
		}else{
			// Otherwise we just unread the next character and that's it.
			input.unread(c);
			return true;
		}
	}
	
	private static void unread(PushbackInputStream s, byte[] bytes, int i) throws IOException{
		while(i >= 0){
			s.unread(bytes[i--]);
		}	
	}
}
