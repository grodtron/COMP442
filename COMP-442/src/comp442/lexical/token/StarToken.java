package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class StarToken extends Token {

	public StarToken(int lineno) {
		super(tok_star, "*", lineno);
	}

}
