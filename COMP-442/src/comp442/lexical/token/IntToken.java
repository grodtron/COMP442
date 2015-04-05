package comp442.lexical.token;

import static comp442.lexical.token.TokenType.tok_int_literal;

public class IntToken extends Token {

	public IntToken(String lexeme, int lineno) {
		super(tok_int_literal, lexeme, lineno);
	}

}
