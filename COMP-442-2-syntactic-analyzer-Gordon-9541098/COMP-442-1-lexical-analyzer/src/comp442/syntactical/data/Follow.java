package comp442.syntactical.data;

import static comp442.syntactical.data.Symbol.END_MARKER;
import static comp442.syntactical.data.Symbol.EPSILON;
import static comp442.syntactical.data.Symbol.prog;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Follow {

	private final static Map<Symbol, Set<Symbol>> _follows;

	
	static {
		_follows = new HashMap<Symbol, Set<Symbol>>();
		
		// Every nonterminal will have a follows set, even if it's just an empty set
		for(Symbol s : Symbol.nonterminals){
			_follows.put(s, new HashSet<Symbol>());
		}
		
		// The end marker is automatically in the the follows set of the start symbol
		_follows.get(prog).add(END_MARKER);
		
		
		boolean changed = true;
		while(changed){
			changed = false;
			// For every production
			for(Entry<Symbol, Symbol[][]> production : Grammar.productions.entrySet()){
				Symbol lhs = production.getKey();
				// For every alternative Right hand side of that production
				for(Symbol[] rhs : production.getValue()){
					// We iterate the whole production
					for(int i = 0; i < rhs.length; ++i){
						// For each non-terminal we encounter
						if(! rhs[i].isTerminal){
							// We add to its Follows set, the First set of the symbol immediately following it
							// As well as the first set of each additional symbol after, if all the symbols between
							// them are nullable.
							Set<Symbol> toAdd = new HashSet<Symbol>();
							int j;
							for(j = i + 1; j < rhs.length; ++j){
								toAdd.addAll(First.get(rhs[j]));
								if(toAdd.contains(EPSILON)){
									toAdd.remove(EPSILON);
								}else{
									break;
								}
							}
							// Finally if we are at the end or all symbols till the end ar nullable, then
							// we add the full follows set of the LHS.
							if(j == rhs.length){
								toAdd.addAll(_follows.get(lhs));
							}
							
							// keep track of whether or not we changed anything
							changed = _follows.get(rhs[i]).addAll(toAdd) || changed;
						}
					}
				}
			}
		}
	}

	public static Set<Symbol> get(Symbol s) {
		if(s.isTerminal){
			return Collections.emptySet();
		}else{
			return _follows.get(s);
		}
	}

	public static Set<Symbol> get(Symbol s []) {
		return get(s[0]);
	}
	
	public static void main(String[] args) {
		for(Symbol s : Symbol.nonterminals){
			System.out.print(s + " => ");System.out.println(Arrays.toString(get(s).toArray()));
		}
	}
	
}
