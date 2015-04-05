package comp442.test.lexical.scanner;

import static comp442.lexical.token.TokenType.tok_float_literal;
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
public class Floats {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {     
			{ "zero"             , "0.0" },
			{ "half"             , "0.5" },
			{ "one"              , "1.0" },
			{ "100.0"              , "100.0" },
			{ "0.001"              , "0.001" },
			{ "very long number" , "1234567890001234567890000.1234567890000000000000001" },
		});
	}

	private final String lexeme;
	private final InputStream stream;

	private final Scanner s;
	
	public Floats(String name, String word){
		this.lexeme = word;
		this.stream = new ByteArrayInputStream(
						(" \t" + word + "\n").getBytes());
		s = new Scanner(this.stream);
	}
	
	@Test
	public void test() throws IOException, InvalidCharacterException {
		Token t = s.getNext();
		
		assertEquals(tok_float_literal, t.token);
		assertEquals(lexeme, t.lexeme);
		assertEquals(1,      t.lineno);
	}

}
