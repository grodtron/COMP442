package comp442.test.lexical.scanner;

import static comp442.lexical.token.TokenType.tok_int_literal;
import static comp442.lexical.token.TokenType.tok_semicolon;
import static comp442.lexical.token.TokenType.tok_slash;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import comp442.lexical.InvalidCharacterException;
import comp442.lexical.LexableInputStream;
import comp442.lexical.Scanner;
import comp442.lexical.token.Token;

public class Comments {
	
	@Test
	public void test_getNext_Slashes() throws IOException, InvalidCharacterException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String(" 1 / 2; // \n").getBytes()), 2);

		Scanner s = new Scanner(in);
		Token t;
		
		t = s.getNext();
		assertEquals(tok_int_literal,   t.token);
		t = s.getNext();
		assertEquals(tok_slash,   t.token);
		t = s.getNext();
		assertEquals(tok_int_literal,   t.token);
		t = s.getNext();
		assertEquals(tok_semicolon,   t.token);
		t = s.getNext();
		assertEquals(null,   t);
		
	}
	
	@Test
	public void test_getNext_SingleLineComment() throws IOException, InvalidCharacterException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String(" // int x = 2;\n").getBytes()), 2);

		Scanner s = new Scanner(in);
		Token t;
		
		t = s.getNext();
		assertEquals(null,   t);
	}
	
	@Test
	public void test_getNext_MultilineComment() throws IOException, InvalidCharacterException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String(" /* int x = 2;\nfloat y = 3.14*/ ").getBytes()), 2);

		Scanner s = new Scanner(in);
		Token t;
		
		t = s.getNext();
		assertEquals(null,   t);
	}

}
