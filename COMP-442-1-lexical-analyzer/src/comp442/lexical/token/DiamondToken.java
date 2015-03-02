package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class DiamondToken extends Token {

	public DiamondToken(int lineno) {
		super(tok_diamond, "<>", lineno);
	}

}
