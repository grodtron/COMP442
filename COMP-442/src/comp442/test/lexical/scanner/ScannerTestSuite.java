package comp442.test.lexical.scanner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	ScannerTest.class,
	ReservedWords.class,
	Ids.class,
	Ints.class,
	Floats.class,
	Punctuation.class,
	Comments.class,
	StrangeCases.class
})
public class ScannerTestSuite {

}
