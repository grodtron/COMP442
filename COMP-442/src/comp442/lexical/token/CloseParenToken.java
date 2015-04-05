package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class CloseParenToken extends Token {

	public CloseParenToken(int lineno) {
		super(tok_close_paren, ")", lineno);
	}

}
