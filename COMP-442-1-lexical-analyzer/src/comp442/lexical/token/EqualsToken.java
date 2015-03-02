package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class EqualsToken extends Token {

	public EqualsToken(int lineno) {
		super(tok_equals, "==", lineno);
	}

}
