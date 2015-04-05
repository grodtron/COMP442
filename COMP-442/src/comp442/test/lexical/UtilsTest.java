package comp442.test.lexical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import comp442.lexical.LexableInputStream;
import comp442.lexical.utils.Consumer;
import comp442.lexical.utils.Matcher;

public class UtilsTest {

	@Test
	public void test_Matcher_matchesReservedWord_whenOnlyReservedWord() throws IOException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("test").getBytes()));
		
		assertTrue(Matcher.matchesReservedWord(in, "test"));
		assertEquals(-1, in.read());
	}

	@Test
	public void test_Matcher_matchesReservedWord_whenFollowedByWhitespace() throws IOException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("test ").getBytes()));
		
		assertTrue(Matcher.matchesReservedWord(in, "test"));
		assertEquals(' ', in.read());
		assertEquals(-1 , in.read());
	}

	@Test
	public void test_Matcher_matchesReservedWord_whenFollowedByPunctuation() throws IOException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("test:").getBytes()));
		
		assertTrue(Matcher.matchesReservedWord(in, "test"));
		assertEquals(':', in.read());
		assertEquals(-1 , in.read());
	}

	@Test
	public void test_Matcher_matchesReservedWord_whenFollowedByAlphaChars() throws IOException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("Testa").getBytes()), 5);
		
		assertTrue( ! Matcher.matchesReservedWord(in, "Test"));
		assertEquals('T', in.read());
		assertEquals('e', in.read());
		assertEquals('s', in.read());
		assertEquals('t', in.read());
		assertEquals('a', in.read());
		assertEquals(-1 , in.read());
	}

	@Test
	public void test_Matcher_matchesReservedWord_whenFollowedByDigit() throws IOException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("Test0").getBytes()), 5);
		
		assertTrue( ! Matcher.matchesReservedWord(in, "Test"));
		assertEquals('T', in.read());
		assertEquals('e', in.read());
		assertEquals('s', in.read());
		assertEquals('t', in.read());
		assertEquals('0', in.read());
		assertEquals(-1 , in.read());
	}

	@Test
	public void test_Matcher_matchesReservedWord_whenFollowedByUnderscore() throws IOException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("Test_").getBytes()), 5);
		
		assertTrue( ! Matcher.matchesReservedWord(in, "Test"));
		assertEquals('T', in.read());
		assertEquals('e', in.read());
		assertEquals('s', in.read());
		assertEquals('t', in.read());
		assertEquals('_', in.read());
		assertEquals(-1 , in.read());
	}
	
	@Test
	public void test_Consumer_consumeWhitespace_whenStartsWithWhitespace() throws IOException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("  Test").getBytes()), 5);
		
		Consumer.consumeWhitespace(in);
		
		assertEquals('T', in.read());
		assertEquals('e', in.read());
		assertEquals('s', in.read());
		assertEquals('t', in.read());
		assertEquals(-1 , in.read());		
	}

	@Test
	public void test_Consumer_consumeWhitespace_whenNotStartsWithWhitespace() throws IOException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("Test ").getBytes()), 5);
		
		Consumer.consumeWhitespace(in);
		
		assertEquals('T', in.read());
		assertEquals('e', in.read());
		assertEquals('s', in.read());
		assertEquals('t', in.read());
		assertEquals(' ', in.read());
		assertEquals(-1 , in.read());		
	}

	@Test
	public void test_Consumer_consumeWhitespace_whenCombinationOfWhitespace() throws IOException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("  \t\t  Test").getBytes()), 5);
		
		Consumer.consumeWhitespace(in);
		
		assertEquals('T', in.read());
		assertEquals('e', in.read());
		assertEquals('s', in.read());
		assertEquals('t', in.read());
		assertEquals(-1 , in.read());		
	}

	@Test
	public void test_Consumer_consumeWhitespace_whenContainsNewline() throws IOException {
		LexableInputStream in = new LexableInputStream(new ByteArrayInputStream(new String("  \nTest").getBytes()), 5);
		
		Consumer.consumeWhitespace(in);
		
		assertEquals(2, in.getLineNumber());
		
		assertEquals('T', in.read());
		assertEquals('e', in.read());
		assertEquals('s', in.read());
		assertEquals('t', in.read());
		assertEquals(-1 , in.read());		
	}

	
}
