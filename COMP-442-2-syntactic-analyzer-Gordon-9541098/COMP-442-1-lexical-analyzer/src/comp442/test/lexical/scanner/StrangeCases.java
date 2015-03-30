package comp442.test.lexical.scanner;

import static comp442.lexical.token.TokenType.tok_assignment;
import static comp442.lexical.token.TokenType.tok_dot;
import static comp442.lexical.token.TokenType.tok_equals;
import static comp442.lexical.token.TokenType.tok_float_literal;
import static comp442.lexical.token.TokenType.tok_id;
import static comp442.lexical.token.TokenType.tok_int_literal;
import static comp442.lexical.token.TokenType.tok_star;
import static comp442.lexical.token.TokenType.tok_slash;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
public class StrangeCases {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {     
			{ "123abc"             , new Object[]{ tok_int_literal, tok_id } },
			{ "123.abc"            , new Object[]{ tok_int_literal, tok_dot,  tok_id } },
			{ "123.00"             , new Object[]{ tok_float_literal, tok_int_literal } },
			{ "0123.00"            , new Object[]{ tok_int_literal, tok_float_literal, tok_int_literal } },
			{ "==="                , new Object[]{ tok_equals, tok_assignment } },
			{ "///*\n*/ "          , new Object[]{ tok_star, tok_slash  } },
			{ "*/*/*/*"            , new Object[]{ tok_star, tok_star } }
		});
	}

	private final Object [] expected;
	private final InputStream stream;

	private final Scanner s;
	
	public StrangeCases(String string, Object[] expected){
		this.stream = new ByteArrayInputStream(string.getBytes());
		this.s      = new Scanner(this.stream);
		this.expected = expected;
	}
	
	@Test
	public void test() throws IOException, InvalidCharacterException {
		for(int i = 0; i < expected.length; ++i){
			Token t = s.getNext();
			assertEquals(t.token, expected[i]);
			
		}
		assertNull(s.getNext());
	}

}
