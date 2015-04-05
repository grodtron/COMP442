package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class CloseBraceToken extends Token {

	public CloseBraceToken(int lineno) {
		super(tok_close_brace, "}", lineno);
	}

}
