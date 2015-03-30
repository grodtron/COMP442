package comp442.test.syntactical.grammar;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import comp442.syntactical.data.Grammar;
import comp442.syntactical.data.Symbol;

/**
 * Test to ensure that no indirectly/mutually left-recursive rules exist
 *
 */
@RunWith(Parameterized.class)
public class LeftRecursion {
	
	@Parameters(name="{0}")
	public static Iterable<Object[]> data(){
		List<Object[]> result = new ArrayList<Object[]>();
		for(Symbol symbol : Symbol.nonterminals){
			result.add(new Object[]{ symbol });
		}
		
		return result;
	}
	
	private final Symbol symbol;

	public LeftRecursion(Symbol s){
		symbol = s;
	}
	
	@Test
	public void test_hasNoLeftRecursion() {
		assertFalse(hasLeftRecursion(symbol));
	}
	
	private boolean hasLeftRecursion(Symbol n){
		for(Symbol[] production : Grammar.productions.get(n)){
			if(hasLeftRecursion(n, production)){
				return true;
			}
		}
		
		return false;
	}

	private boolean hasLeftRecursion(Symbol n, Symbol[] production) {
		if(production.length == 0 || production[0].isTerminal()){
			return false;
		}else if(production[0] == n){
			return true;
		}else{
			for(Symbol[] p : Grammar.productions.get(production[0])){
				if(hasLeftRecursion(n, p)){
					return true;
				}
				
			}
			return false;
		}
	}

}
