package comp442.lexical.state;

import java.io.IOException;

import comp442.lexical.LexableInputStream;
import comp442.lexical.token.Token;
import comp442.lexical.token.ReservedWordToken;

public class GotReservedWord extends State {

	private final ReservedWordToken token;
	
	public GotReservedWord(String word, int lineno){
		token = new ReservedWordToken(word, lineno);
	}
	
	@Override
	public State process(LexableInputStream input) throws IOException {
		return new Start();
	}
	
	@Override
	public boolean nextTokenReady(){
		return true;
	}
	
	@Override
	public Token getToken(){
		return token;
	}

}
