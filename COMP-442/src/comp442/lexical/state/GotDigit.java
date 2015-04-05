package comp442.lexical.state;

import java.io.IOException;

import comp442.lexical.LexableInputStream;
import comp442.lexical.token.Token;
import comp442.lexical.token.IntToken;
import comp442.lexical.utils.Consumer;

public class GotDigit extends State {

	private final StringBuilder s;
	private boolean tokenReady;
	private int lineno;
	
	boolean startsWithZero;
	
	public GotDigit(int c) {
		this.s = new StringBuilder();
		this.tokenReady = false;
		s.append((char)c);
		startsWithZero = (c == '0');
	}

	
	@Override
	public State process(LexableInputStream input) throws IOException {
		if(tokenReady){
			return new Start();
		}
		
		if(!startsWithZero){
			Consumer.consumeDigits(input, s);			
		}
		
		// at this point we have an integer, and we need to validate the following character
		
		int c = input.read();
		if(c == '.'){
			// we actually have a float (maybe)
			return new GotIntDot(s);
		}else{
			// we have a complete token
			input.unread(c);
			tokenReady = true;
			lineno = input.getLineNumber();
			return this;
		}
	}
	
	@Override
	public boolean nextTokenReady(){
		return tokenReady;
	}
	
	@Override
	public Token getToken(){
		if(tokenReady){
			return new IntToken(s.toString(), lineno);
		}else{
			return null;
		}
	}
}
