package comp442.test.semantic;

import java.io.File;

import comp442.semantic.action.SemanticContext;
import comp442.semantic.symboltable.entries.SymbolTableEntry;
import comp442.syntactical.parser.Parser;

public class Test {

	@org.junit.Test
	public void test() throws Exception {
		Parser p = new Parser(new File("samples/correct-code/semantic-test.code"));
		
		p.parse();
		
		SymbolTableEntry test = SemanticContext.find("test");
		
	}

}
