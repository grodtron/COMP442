package comp442.lexical.token;


public class ReservedWordToken extends Token {

	public ReservedWordToken(String word, int lineno) {
		super(TokenType.valueOf("tok_" + word), word, lineno);
	}
	
}
