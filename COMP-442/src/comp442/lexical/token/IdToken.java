package comp442.lexical.token;

import static comp442.lexical.token.TokenType.tok_id;

public class IdToken extends Token {

	public IdToken(String lexeme, int lineno) {
		super(tok_id, lexeme, lineno);
	}

}
