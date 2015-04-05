package comp442.lexical.state;

import java.io.IOException;

import comp442.lexical.LexableInputStream;
import comp442.lexical.token.Token;
import comp442.lexical.token.IdToken;
import comp442.lexical.utils.Consumer;

public class GotAlpha extends State {

	private final StringBuilder s;
	private boolean tokenReady;
	private int lineno;
	
	public GotAlpha(int c) {
		this.s = new StringBuilder();
		this.tokenReady = false;
		s.append((char)c);
	}

	@Override
	public State process(LexableInputStream input) throws IOException {
		if(tokenReady){
			return new Start();
		}else{
			Consumer.consumeAlphanumOrUnderscore(input, s);
			this.tokenReady = true;
			this.lineno = input.getLineNumber();
			
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
			return new IdToken(s.toString(), lineno);
		}else{
			return null;
		}
	}

}
