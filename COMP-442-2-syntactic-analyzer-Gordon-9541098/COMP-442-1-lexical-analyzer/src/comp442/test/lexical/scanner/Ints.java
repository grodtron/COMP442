package comp442.test.lexical.scanner;

import static comp442.lexical.token.TokenType.tok_int_literal;
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

@RunWith(Parameterized.class)
public class Ints {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {     
			{ "zero"             , "0" },
			{ "one"              , "1" },
			{ "100"              , "100" },
			{ "very long number" , "1234567890001234567890000123456789000000000000000" }
		});
	}

	private final String lexeme;
	private final InputStream stream;

	private final Scanner s;
	
	public Ints(String name, String word){
		this.lexeme = word;
		this.stream = new ByteArrayInputStream(
						(" \t" + word + "\n").getBytes());
		s = new Scanner(this.stream);
	}
	
	@Test
	public void test() throws IOException, InvalidCharacterException {
		Token t = s.getNext();
		
		assertEquals(tok_int_literal, t.token);
		assertEquals(lexeme, t.lexeme);
		assertEquals(1,      t.lineno);
	}

}
