package comp442.lexical.token;

import static comp442.lexical.token.TokenType.tok_close_square;

public class CloseSquareToken extends Token {

	public CloseSquareToken(int lineno) {
		super(tok_close_square, "]", lineno);
	}

}
