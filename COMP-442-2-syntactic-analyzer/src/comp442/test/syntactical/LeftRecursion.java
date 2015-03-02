package comp442.test.syntactical;

import static org.junit.Assert.*;

import org.junit.Test;

import comp442.syntactical.data.Grammar;
import comp442.syntactical.data.Symbol;

/**
 * Test to ensure that no indirectly/mutually left-recursive rules exist
 *
 */
public class LeftRecursion {

	@Test
	public void test() {
		for(Symbol n : Symbol.nonterminals){
			assertFalse(hasLeftRecursion(n));
		}
	}
	
	private boolean hasLeftRecursion(Symbol n){
		System.out.println("testing " + n.name());
		for(Symbol[] production : Grammar.productions.get(n)){
			if(hasLeftRecursion(n, production)){
				return true;
			}
			System.out.println("");
		}
		
		return false;
	}

	private boolean hasLeftRecursion(Symbol n, Symbol[] production) {
		if(production.length == 0 || production[0].isTerminal){
			System.out.print(" -> TERMINAL");
			return false;
		}else if(production[0] == n){
			return true;
		}else{
			for(Symbol[] p : Grammar.productions.get(production[0])){
				System.out.print(" -> " + production[0]);
				if(hasLeftRecursion(n, p)){
					return true;
				}
				
			}
			return false;
		}
	}

}
