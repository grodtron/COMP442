package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class LessThanEqualsToken extends Token {

	public LessThanEqualsToken(int lineno) {
		super(tok_less_than_equals, "<=", lineno);
	}

}
