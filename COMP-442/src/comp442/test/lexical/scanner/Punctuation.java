package comp442.test.lexical.scanner;

import static comp442.lexical.token.TokenType.tok_assignment;
import static comp442.lexical.token.TokenType.tok_close_brace;
import static comp442.lexical.token.TokenType.tok_close_paren;
import static comp442.lexical.token.TokenType.tok_close_square;
import static comp442.lexical.token.TokenType.tok_comma;
import static comp442.lexical.token.TokenType.tok_diamond;
import static comp442.lexical.token.TokenType.tok_dot;
import static comp442.lexical.token.TokenType.tok_equals;
import static comp442.lexical.token.TokenType.tok_greater_than;
import static comp442.lexical.token.TokenType.tok_greater_than_equals;
import static comp442.lexical.token.TokenType.tok_less_than;
import static comp442.lexical.token.TokenType.tok_less_than_equals;
import static comp442.lexical.token.TokenType.tok_minus;
import static comp442.lexical.token.TokenType.tok_open_brace;
import static comp442.lexical.token.TokenType.tok_open_paren;
import static comp442.lexical.token.TokenType.tok_open_square;
import static comp442.lexical.token.TokenType.tok_plus;
import static comp442.lexical.token.TokenType.tok_semicolon;
import static comp442.lexical.token.TokenType.tok_slash;
import static comp442.lexical.token.TokenType.tok_star;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import comp442.lexical.InvalidCharacterException;
import comp442.lexical.Scanner;
import comp442.lexical.token.Token;
import comp442.lexical.token.TokenType;

@RunWith(Parameterized.class)
public class Punctuation {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {     
			{ "=="             , tok_equals },
			{ "<>"             , tok_diamond },
			{ "<"              , tok_less_than },
			{ ">"              , tok_greater_than },
			{ "<="             , tok_less_than_equals },
			{ ">="             , tok_greater_than_equals },
			{ ";"              , tok_semicolon },
			{ ","              , tok_comma },
			{ "."              , tok_dot },
			{ "+"              , tok_plus },
			{ "-"              , tok_minus },
			{ "*"              , tok_star },
			{ "/"              , tok_slash },			
			{ "="             , tok_assignment },
			
			{ "(" , tok_open_paren},
			{ "{", tok_open_brace},
			{ "[", tok_open_square},
			{ ")", tok_close_paren},
			{ "}", tok_close_brace},
			{ "]", tok_close_square},

		});
	}

	private final String lexeme;
	private final TokenType token;
	private final InputStream stream;

	private final Scanner s;
	
	public Punctuation(String lexeme, TokenType token){
		this.lexeme = lexeme;
		this.token  = token;
		this.stream = new ByteArrayInputStream(
						(" \t" + lexeme + "\n").getBytes());
		s = new Scanner(this.stream);
	}
	
	@Test
	public void test() throws IOException, InvalidCharacterException {
		Token t = s.getNext();
		
		assertEquals(token, t.token);
		assertEquals(lexeme, t.lexeme);
		assertEquals(1,      t.lineno);
	}

}
