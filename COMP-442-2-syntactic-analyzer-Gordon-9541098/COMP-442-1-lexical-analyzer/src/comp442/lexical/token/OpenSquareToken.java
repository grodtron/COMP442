package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class OpenSquareToken extends Token {

	public OpenSquareToken(int lineno) {
		super(tok_open_square, "[", lineno);
	}

}
