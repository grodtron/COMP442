package comp442.syntactical.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class First {

	private final static Map<Symbol, Set<Symbol>> _firsts;
	
	private static Set<Symbol> makeFirsts(Symbol s){
		if(_firsts.containsKey(s)){
			return _firsts.get(s);
		}else{
			Set<Symbol> f = new HashSet<Symbol>();
			for(Symbol[] p : Grammar.productions.get(s)){
				for(int i = 0; i < p.length; ++i){
					if(! p[i].isSemanticAction()){
						f.addAll(makeFirsts(p[i]));
						break;
					}
				}
			}
			_firsts.put(s, f);
			return f;
		}
	}
	
	static {
		
		_firsts = new HashMap<Symbol, Set<Symbol>>();
		
		for(Symbol s : Symbol.terminals){
			_firsts.put(s, Collections.singleton(s));
		}
		
		for(Symbol s : Symbol.semanticActions){
			_firsts.put(s, Collections.<Symbol>emptySet());
		}
		
		for(Symbol s : Symbol.nonterminals){
			makeFirsts(s);
		}
	}
	
	public static Set<Symbol> get(Symbol s){
		return _firsts.get(s);
	}

	public static Set<Symbol> get(Symbol s []){
		for(int i = 0; i < s.length; ++i){
			if(! s[i].isSemanticAction()){
				return get(s[i]);
			}
		}
		throw new RuntimeException("Should not deal with sets of ONLY semantic actions");
	}
	
	public static void main(String[] args) {
		for(Symbol s : Symbol.nonterminals){
			System.out.print(s + " => ");System.out.println(Arrays.toString(get(s).toArray()));
		}
	}
	
}
