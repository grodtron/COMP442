package comp442.test.semantic.symbols;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ArrayVariableTest.class,
	ParameterTest.class,
	PlainVariableTest.class
})
public class SymbolCreationTestSuite {

}
