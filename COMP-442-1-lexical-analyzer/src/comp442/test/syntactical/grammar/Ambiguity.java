package comp442.test.syntactical.grammar;

import static comp442.syntactical.data.Symbol.EPSILON;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import comp442.syntactical.data.First;
import comp442.syntactical.data.Follow;
import comp442.syntactical.data.Grammar;
import comp442.syntactical.data.Symbol;

@RunWith(Parameterized.class)
public class Ambiguity {

	@Parameters(name="{0} - {1},{2}")
	public static Collection<Object[]> data(){
		List<Object[]> result = new ArrayList<Object[]>();
		for(Symbol s : Symbol.nonterminals){
			Symbol [][] productions = Grammar.productions.get(s);
			for(int i = 0; i < productions.length; ++i){
				for(int j = 0; j < i; ++j){
					result.add(new Object[]{
						s, i, j
					});
				}
			}
		}
		
		return result;
	}
	
	@Parameter(0)
	public Symbol symbol;
	
	@Parameter(1)
	public int i;
	
	@Parameter(2)
	public int j;
	
	@Test
	public void test_isNotAmbiguous() {
		Symbol [][] productions = Grammar.productions.get(symbol);

		Set<Symbol> intersectA = new HashSet<Symbol>(First.get(productions[i]));
		if(intersectA.contains(EPSILON)){
			intersectA.addAll(Follow.get(productions[i]));
		}
		Set<Symbol> intersectB = new HashSet<Symbol>(First.get(productions[j]));
		if(intersectB.contains(EPSILON)){
			intersectB.addAll(Follow.get(productions[j]));
		}
		
		intersectA.retainAll(intersectB);
		
		assertEquals(intersectA, Collections.emptySet());
	}

}
