package comp442.test.lexical.scanner;

import static comp442.lexical.token.TokenType.tok_and;
import static comp442.lexical.token.TokenType.tok_class;
import static comp442.lexical.token.TokenType.tok_else;
import static comp442.lexical.token.TokenType.tok_float;
import static comp442.lexical.token.TokenType.tok_for;
import static comp442.lexical.token.TokenType.tok_get;
import static comp442.lexical.token.TokenType.tok_if;
import static comp442.lexical.token.TokenType.tok_int;
import static comp442.lexical.token.TokenType.tok_not;
import static comp442.lexical.token.TokenType.tok_or;
import static comp442.lexical.token.TokenType.tok_put;
import static comp442.lexical.token.TokenType.tok_return;
import static comp442.lexical.token.TokenType.tok_then;
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
public class ReservedWords {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {     
			{ "and",    tok_and },
			{ "not",    tok_not },
			{ "or",     tok_or },
			{ "if",     tok_if },
			{ "then",   tok_then },
			{ "else",   tok_else },
			{ "for",    tok_for },
			{ "class",  tok_class },
			{ "int",    tok_int },
			{ "float",  tok_float },
			{ "get",    tok_get },
			{ "put",    tok_put },
			{ "return", tok_return }
		});
	}

	private final String lexeme;
	private final TokenType token;
	private final InputStream stream;

	private final Scanner s;
	
	public ReservedWords(String word, TokenType token){
		this.lexeme = word;
		this.token = token;
		this.stream = new ByteArrayInputStream(
						(" \t" + word + "\n").getBytes());
		s = new Scanner(this.stream);
	}
	
	@Test
	public void test() throws IOException, InvalidCharacterException {
		Token t = s.getNext();
		
		assertEquals(token,  t.token);
		assertEquals(lexeme, t.lexeme);
		assertEquals(1,      t.lineno);
	}

}
