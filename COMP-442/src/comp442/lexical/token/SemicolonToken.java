package comp442.lexical.token;

import static comp442.lexical.token.TokenType.tok_semicolon;

public class SemicolonToken extends Token {

	public SemicolonToken(int lineno) {
		super(tok_semicolon, ";", lineno);
	}

}
