package comp442.lexical.state;

import comp442.lexical.token.Token;

import static comp442.lexical.token.TokenType.tok_float_literal;

public class FloatToken extends Token {

	public FloatToken(String lexeme, int lineno){
		super(tok_float_literal, lexeme, lineno);
	}
	
}
