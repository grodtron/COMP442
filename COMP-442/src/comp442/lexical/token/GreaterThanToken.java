package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class GreaterThanToken extends Token {

	public GreaterThanToken(int lineno) {
		super(tok_greater_than, ">", lineno);
	}

}
