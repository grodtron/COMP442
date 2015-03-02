package comp442.lexical.token;

import static comp442.lexical.token.TokenType.tok_dot;

public class DotToken extends Token {

	public DotToken(int lineno) {
		super(tok_dot, ".", lineno);
	}

}
