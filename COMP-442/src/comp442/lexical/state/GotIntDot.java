package comp442.lexical.state;

import java.io.IOException;

import comp442.lexical.LexableInputStream;
import comp442.lexical.token.Token;
import comp442.lexical.token.IntToken;
import comp442.lexical.utils.Consumer;

public class GotIntDot extends State {

	private final StringBuilder s;
	private Token token;
	
	public GotIntDot(StringBuilder s){
		this.s = s;
		this.token = null;
	}
	
	@Override
	public State process(LexableInputStream input) throws IOException {
		if(token != null){
			return new Start();
		}
		
		int c = input.read();
		if(Character.isDigit(c)){
			s.append('.');
			s.append((char)c);
			Consumer.consumeDigits(input, s, true);
			token = new FloatToken(s.toString(), input.getLineNumber());
		}else{
			input.unread(c);
			input.unread('.');
			token = new IntToken(s.toString(), input.getLineNumber());
		}
		
		return this;
	}
	
	@Override
	public boolean nextTokenReady(){
		return token != null;
	}
	
	@Override
	public Token getToken(){
		return token;
	}
}
