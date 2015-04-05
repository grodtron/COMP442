package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class PlusToken extends Token {

	public PlusToken(int lineno) {
		super(tok_plus, "+", lineno);
	}

}
