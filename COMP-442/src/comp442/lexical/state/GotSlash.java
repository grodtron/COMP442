package comp442.lexical.state;

import java.io.IOException;

import comp442.lexical.LexableInputStream;
import comp442.lexical.token.Token;
import comp442.lexical.token.SlashToken;
import comp442.lexical.utils.Consumer;

public class GotSlash extends State {

	private Token token;
	
	public GotSlash() {
		token = null;
	}
	
	@Override
	public State process(LexableInputStream input) throws IOException {
		if(token != null) return new Start();
		
		int c = input.read();
		if( c == '/' ){
			Consumer.consumeUntil(input, "\n");
			return new Start();
		}else
		if(c == '*'){
			Consumer.consumeUntil(input, "*/");
			return new Start();
		}else{
			input.unread(c);
			token = new SlashToken(input.getLineNumber());
			
			return this;
		}
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
