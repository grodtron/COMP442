package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class CommaToken extends Token {

	public CommaToken(int lineno) {
		super(tok_comma, ",", lineno);
	}

}
