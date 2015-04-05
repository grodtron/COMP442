package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class GreaterThanEqualsToken extends Token {

	public GreaterThanEqualsToken(int lineno) {
		super(tok_greater_than_equals, ">=", lineno);
	}

}
