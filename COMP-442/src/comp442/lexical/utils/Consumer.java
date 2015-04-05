package comp442.lexical.utils;

import java.io.IOException;
import java.io.PushbackInputStream;

public class Consumer {
	/**
	 * Consume whitespace from a stream until a non whitespace character is encountered.
	 * 
	 * @param in The input stream to consume from
	 * @throws IOException if a stream operation throws an IOException
	 */
	public static void consumeWhitespace(PushbackInputStream in) throws IOException{
		int c;
		
		do{
			c = in.read();
		}while(Character.isWhitespace(c));
		
		in.unread(c);
	}
	
	public static void consumeAlphanumOrUnderscore(PushbackInputStream in, StringBuilder s) throws IOException{
		int c;
		
		while(true){
			c = in.read();
			if( Character.isLetterOrDigit(c) || c == '_' ){
				s.append((char)c);
			}else{
				break;
			}
		}
		
		in.unread(c);
	}
	
	public static void consumeDigits(PushbackInputStream in, StringBuilder s) throws IOException{
		consumeDigits(in, s, false);
	}

	public static void consumeDigits(PushbackInputStream in, StringBuilder s, boolean exceptLastZero) throws IOException{
		int c;
		
		while(true){
			c = in.read();
			if( Character.isDigit(c) ){
				if(exceptLastZero && c == '0'){
					c = in.read();
					if(!Character.isDigit(c)){
						in.unread('0');
						in.unread(c);
						break;
					}else{
						in.unread(c);
						c = '0';
					}
				}
				s.append((char)c);
			}else{
				break;
			}
		}
		
		in.unread(c);
	}
	
	public static void consumeUntil(PushbackInputStream in, String needle) throws IOException {
		byte [] needleBuff = needle.getBytes();
		int i = 0;
		
		while(in.available() > 0){
			int c = in.read();
			if(c == needleBuff[i]){
				++i;
				if(i == needleBuff.length){
					return;
				}
			}else{
				i = 0;
			}
		}
	}
}
