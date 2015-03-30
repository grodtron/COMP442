package comp442.test.syntactical.grammar;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	Ambiguity.class,
	LeftRecursion.class
})
public class ValidateGrammar {

}
