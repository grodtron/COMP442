package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class MinusToken extends Token {

	public MinusToken(int lineno) {
		super(tok_minus, "-", lineno);
	}

}
