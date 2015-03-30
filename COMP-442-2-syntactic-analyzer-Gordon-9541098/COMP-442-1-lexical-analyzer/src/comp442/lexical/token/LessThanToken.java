package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class LessThanToken extends Token {

	public LessThanToken(int lineno) {
		super(tok_less_than, "<", lineno);
	}

}
