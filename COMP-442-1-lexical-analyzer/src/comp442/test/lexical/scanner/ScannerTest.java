package comp442.test.lexical.scanner;

import static comp442.lexical.token.TokenType.tok_and;

import static comp442.lexical.token.TokenType.tok_assignment;
import static comp442.lexical.token.TokenType.tok_close_brace;
import static comp442.lexical.token.TokenType.tok_close_paren;
import static comp442.lexical.token.TokenType.tok_close_square;
import static comp442.lexical.token.TokenType.tok_comma;
import static comp442.lexical.token.TokenType.tok_else;
import static comp442.lexical.token.TokenType.tok_equals;
import static comp442.lexical.token.TokenType.tok_float;
import static comp442.lexical.token.TokenType.tok_float_literal;
import static comp442.lexical.token.TokenType.tok_for;
import static comp442.lexical.token.TokenType.tok_id;
import static comp442.lexical.token.TokenType.tok_if;
import static comp442.lexical.token.TokenType.tok_int;
import static comp442.lexical.token.TokenType.tok_int_literal;
import static comp442.lexical.token.TokenType.tok_less_than;
import static comp442.lexical.token.TokenType.tok_open_brace;
import static comp442.lexical.token.TokenType.tok_open_paren;
import static comp442.lexical.token.TokenType.tok_open_square;
import static comp442.lexical.token.TokenType.tok_plus;
import static comp442.lexical.token.TokenType.tok_return;
import static comp442.lexical.token.TokenType.tok_semicolon;
import static comp442.lexical.token.TokenType.tok_star;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import comp442.lexical.InvalidCharacterException;
import comp442.lexical.LexableInputStream;
import comp442.lexical.Scanner;
import comp442.lexical.token.Token;

public class ScannerTest {

	@Test
	public void test_getNext_ifElseMultiLine() throws IOException, InvalidCharacterException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("if\n"
				+ "else\n").getBytes()), 2);

		Scanner s = new Scanner(in);
		Token t;
		
		t = s.getNext();
		assertEquals(tok_if, t.token);
		assertEquals("if",   t.lexeme);
		assertEquals(1,      t.lineno);

		t = s.getNext();
		assertEquals(tok_else, t.token);
		assertEquals("else",   t.lexeme);
		assertEquals(2,      t.lineno);
	}
	
	@Test
	public void test_getNext_idAndReservedWord() throws IOException, InvalidCharacterException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("int x\nfloat y\n").getBytes()), 2);

		Scanner s = new Scanner(in);
		Token t;
		
		t = s.getNext();
		assertEquals("int",   t.lexeme);
		assertEquals(tok_int, t.token);
		assertEquals(1,       t.lineno);
		t = s.getNext();
		assertEquals("x",    t.lexeme);
		assertEquals(tok_id, t.token);
		assertEquals(1,      t.lineno);

		t = s.getNext();
		assertEquals("float",   t.lexeme);
		assertEquals(tok_float, t.token);
		assertEquals(2,         t.lineno);
		t = s.getNext();
		assertEquals("y",    t.lexeme);
		assertEquals(tok_id, t.token);
		assertEquals(2,      t.lineno);
	}

	@Test
	public void test_getNext_floatLiteralReservedWordId() throws IOException, InvalidCharacterException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("0.5 and x").getBytes()), 2);

		Scanner s = new Scanner(in);
		Token t;
		
		t = s.getNext();
		assertEquals("0.5",   t.lexeme);
		assertEquals(tok_float_literal, t.token);
		assertEquals(1,       t.lineno);
		t = s.getNext();
		assertEquals("and",    t.lexeme);
		assertEquals(tok_and, t.token);
		assertEquals(1,      t.lineno);
		t = s.getNext();
		assertEquals("x",    t.lexeme);
		assertEquals(tok_id, t.token);
		assertEquals(1,      t.lineno);
	}

	@Test
	public void test_getNext_intLiteralReservedWordId() throws IOException, InvalidCharacterException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("3 and x").getBytes()), 2);

		Scanner s = new Scanner(in);
		Token t;
		
		t = s.getNext();
		assertEquals("3",   t.lexeme);
		assertEquals(tok_int_literal, t.token);
		assertEquals(1,       t.lineno);
		t = s.getNext();
		assertEquals("and",    t.lexeme);
		assertEquals(tok_and, t.token);
		assertEquals(1,      t.lineno);
		t = s.getNext();
		assertEquals("x",    t.lexeme);
		assertEquals(tok_id, t.token);
		assertEquals(1,      t.lineno);
	}
	
	@Test
	public void test_getNext_simpleProgram() throws IOException, InvalidCharacterException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(
				new String(
						"int main (int argc, int ** argv) {\n"
				      + "   // simply print the number 2\n"
				      + "   int number = 2;\n"
				      + "   print(number);\n"
				      + "   return 0;\n"
				      + "}"
						).getBytes()), 2);
		
		Scanner s = new Scanner(in);

		Object [][] expected = new Object[][]{
				{tok_int, "int", 1},
				{tok_id, "main", 1},
				{tok_open_paren, "(", 1},
				{tok_int, "int", 1},
				{tok_id, "argc", 1},
				{tok_comma, ",", 1},
				{tok_int, "int", 1},
				{tok_star, "*", 1},
				{tok_star, "*", 1},
				{tok_id, "argv", 1},
				{tok_close_paren, ")", 1},
				{tok_open_brace, "{", 1},
				{tok_int, "int", 3},
				{tok_id, "number", 3},
				{tok_assignment, "=", 3},
				{tok_int_literal, "2", 3},
				{tok_semicolon, ";", 3},
				{tok_id, "print", 4},
				{tok_open_paren, "(", 4},
				{tok_id, "number", 4},
				{tok_close_paren, ")", 4},
				{tok_semicolon, ";", 4},
				{tok_return, "return", 5},
				{tok_int_literal, "0", 5},
				{tok_semicolon, ";", 5},
				{tok_close_brace, "}", 6}
		};
		
		for(int i = 0; i < expected.length; ++i){
			Token tok = s.getNext();
			
			assertEquals(expected[i][0], tok.token);
			assertEquals(expected[i][1], tok.lexeme);
			assertEquals(expected[i][2], tok.lineno);
		}
		
		Token tok = s.getNext();
		
		assertEquals(null, tok);
		
	}
	
	@Test
	public void test_getNext_mergeSort() throws IOException, InvalidCharacterException{
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(
				new String(
		"void merge (int *a, int n, int m) {\n" +
		"    int i, j, k;\n" +
		"    int *x = malloc(n * sizeof (int));\n" +
		"    for (i = 0, j = m, k = 0; k < n; k++) {\n" +
		"        x[k] = j == n      ? a[i++]\n" +
		"             : i == m      ? a[j++]\n" +
		"             : a[j] < a[i] ? a[j++]\n" +
		"             :               a[i++];\n" +
		"    }\n" +
		"    memcpy(a, x, n * sizeof (int));\n" +
		"    free(x);\n" +
		"}\n" +
		" \n"
				).getBytes()), 2);
		
		Scanner s = new Scanner(in);

		Object [][] expected = new Object[][]{
				{tok_id, "void", 1},
				{tok_id, "merge", 1},
				{tok_open_paren, "(", 1},
				{tok_int, "int", 1},
				{tok_star, "*", 1},
				{tok_id, "a", 1},
				{tok_comma, ",", 1},
				{tok_int, "int", 1},
				{tok_id, "n", 1},
				{tok_comma, ",", 1},
				{tok_int, "int", 1},
				{tok_id, "m", 1},
				{tok_close_paren, ")", 1},
				{tok_open_brace, "{", 1},
				//
				{tok_int, "int", 2},
				{tok_id, "i", 2},
				{tok_comma, ",", 2},
				{tok_id, "j", 2},
				{tok_comma, ",", 2},
				{tok_id, "k", 2},
				{tok_semicolon, ";", 2},
				//
				{tok_int, "int", 3},
				{tok_star, "*", 3},
				{tok_id, "x", 3},
				{tok_assignment, "=", 3},
				{tok_id, "malloc", 3},
				{tok_open_paren, "(", 3},
				{tok_id, "n", 3},
				{tok_star, "*", 3},
				{tok_id, "sizeof", 3},
				{tok_open_paren, "(", 3},
				{tok_int, "int", 3},
				{tok_close_paren, ")", 3},
				{tok_close_paren, ")", 3},
				{tok_semicolon, ";", 3},
				//
				{tok_for, "for", 4},
				{tok_open_paren, "(", 4},
				{tok_id, "i", 4},
				{tok_assignment, "=", 4},
				{tok_int_literal, "0", 4},
				{tok_comma, ",", 4},
				{tok_id, "j", 4},
				{tok_assignment, "=", 4},
				{tok_id, "m", 4},
				{tok_comma, ",", 4},
				{tok_id, "k", 4},
				{tok_assignment, "=", 4},
				{tok_int_literal, "0", 4},
				{tok_semicolon, ";", 4},
				{tok_id, "k", 4},
				{tok_less_than, "<", 4},
				{tok_id, "n", 4},
				{tok_semicolon, ";", 4},
				{tok_id, "k", 4},
				{tok_plus, "+", 4},
				{tok_plus, "+", 4},
				{tok_close_paren, ")", 4},
				{tok_open_brace, "{", 4},
				//
				{tok_id, "x", 5},
				{tok_open_square, "[", 5},
				{tok_id, "k", 5},
				{tok_close_square, "]", 5},
				{tok_assignment, "=", 5},
				{tok_id, "j", 5},
				{tok_equals, "==", 5},
				{tok_id, "n", 5},
				{InvalidCharacterException.class, "?", 5},
				{tok_id, "a", 5},
				{tok_open_square, "[", 5},
				{tok_id, "i", 5},
				{tok_plus, "+", 5},
				{tok_plus, "+", 5},
				{tok_close_square, "]", 5},
				//
				{InvalidCharacterException.class, ":", 6},
				{tok_id, "i", 6},
				{tok_equals, "==", 6},
				{tok_id, "m", 6},
				{InvalidCharacterException.class, "?", 6},
				{tok_id, "a", 6},
				{tok_open_square, "[", 6},
				{tok_id, "j", 6},
				{tok_plus, "+", 6},
				{tok_plus, "+", 6},
				{tok_close_square, "]", 6},
				//
				{InvalidCharacterException.class, ":", 7},
				{tok_id, "a", 7},
				{tok_open_square, "[", 7},
				{tok_id, "j", 7},
				{tok_close_square, "]", 7},
				{tok_less_than, "<", 7},
				{tok_id, "a", 7},
				{tok_open_square, "[", 7},
				{tok_id, "i", 7},
				{tok_close_square, "]", 7},
				{InvalidCharacterException.class, "?", 7},
				{tok_id, "a", 7},
				{tok_open_square, "[", 7},
				{tok_id, "j", 7},
				{tok_plus, "+", 7},
				{tok_plus, "+", 7},
				{tok_close_square, "]", 7},
				//
				{InvalidCharacterException.class, ":", 8},
				{tok_id, "a", 8},
				{tok_open_square, "[", 8},
				{tok_id, "i", 8},
				{tok_plus, "+", 8},
				{tok_plus, "+", 8},
				{tok_close_square, "]", 8},
				{tok_semicolon, ";", 8},
				//
				{tok_close_brace, "}", 9},
				//
				{tok_id, "memcpy", 10},
				{tok_open_paren, "(", 10},
				{tok_id, "a", 10},
				{tok_comma, ",", 10},
				{tok_id, "x", 10},
				{tok_comma, ",", 10},
				{tok_id, "n", 10},
				{tok_star, "*", 10},
				{tok_id, "sizeof", 10},
				{tok_open_paren, "(", 10},
				{tok_int, "int", 10},
				{tok_close_paren, ")", 10},
				{tok_close_paren, ")", 10},
				{tok_semicolon, ";", 10},
				//
				{tok_id, "free", 11},
				{tok_open_paren, "(", 11},
				{tok_id, "x", 11},
				{tok_close_paren, ")", 11},
				{tok_semicolon, ";", 11},
				//
				{tok_close_brace, "}", 12},
		};
		
		for(int i = 0; i < expected.length; ++i){
			try{
				Token tok = s.getNext();

				assertEquals(expected[i][0], tok.token);
				assertEquals(expected[i][1], tok.lexeme);
				assertEquals(expected[i][2], tok.lineno);				
			}catch(InvalidCharacterException e){
				assertEquals(expected[i][0], e.getClass());
				assertEquals(expected[i][1], "" + e.character);
				assertEquals(expected[i][2], e.lineNumber);
			}
		}
		
		Token tok = s.getNext();
		
		assertEquals(null, tok);

	}

}
