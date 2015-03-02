package comp442.lexical.token;

public abstract class Token {

	public final TokenType token;
	public final String    lexeme;
	public final int       lineno;
	
	public Token(TokenType token, String lexeme, int lineno){
		this.token  = token;
		this.lexeme = lexeme;
		this.lineno = lineno;
	}
	
	@Override
	public String toString(){
		return String.format("%1$-20s", token.toString())
			 + "line: " + String.format("%1$-8s", Integer.toString(lineno))
			 + "\"" + lexeme + "\"";
	}
	
}
