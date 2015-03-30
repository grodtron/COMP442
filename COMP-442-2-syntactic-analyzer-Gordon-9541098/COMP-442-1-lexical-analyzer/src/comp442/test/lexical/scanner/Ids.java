package comp442.test.lexical.scanner;

import static comp442.lexical.token.TokenType.tok_id;
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
public class Ids {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {     
			{ "one character"               , "x" },
			{ "alphabetic ID"               , "xyz" },
			{ "alphanumeric ID"             , "a1b2c3" },
			{ "alpha and underscore ID"     , "a_b_c_" },
			{ "alphanmum and underscore ID" , "a_1b_2c_3" },
			{ "long ID"                     , "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_" }
		});
	}

	private final String lexeme;
	private final InputStream stream;

	private final Scanner s;
	
	public Ids(String name, String word){
		this.lexeme = word;
		this.stream = new ByteArrayInputStream(
						(" \t" + word + "\n").getBytes());
		s = new Scanner(this.stream);
	}
	
	@Test
	public void test() throws IOException, InvalidCharacterException {
		Token t = s.getNext();
		
		assertEquals(tok_id, t.token);
		assertEquals(lexeme, t.lexeme);
		assertEquals(1,      t.lineno);
	}

}
