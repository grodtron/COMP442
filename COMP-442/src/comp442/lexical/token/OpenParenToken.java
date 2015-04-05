package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class OpenParenToken extends Token {

	public OpenParenToken(int lineno) {
		super(tok_open_paren, "(", lineno);
	}

}
