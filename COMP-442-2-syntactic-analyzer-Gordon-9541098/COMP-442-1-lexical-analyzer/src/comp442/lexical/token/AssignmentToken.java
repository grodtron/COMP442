package comp442.lexical.token;

import static comp442.lexical.token.TokenType.*;

public class AssignmentToken extends Token {

	public AssignmentToken(int lineno) {
		super(tok_assignment, "=", lineno);
	}

}
