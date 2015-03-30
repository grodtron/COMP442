package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class OpenBraceToken extends Token {

	public OpenBraceToken(int lineno) {
		super(tok_open_brace, "{", lineno);
	}

}
