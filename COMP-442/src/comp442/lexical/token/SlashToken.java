package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class SlashToken extends Token {

	public SlashToken(int lineno) {
		super(tok_slash, "/", lineno);
	}

}
