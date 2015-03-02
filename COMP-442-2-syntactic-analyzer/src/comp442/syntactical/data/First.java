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
				f.addAll(makeFirsts(p[0]));
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
		
		for(Symbol s : Symbol.nonterminals){
			makeFirsts(s);
		}
	}
	
	public static Set<Symbol> get(Symbol s){
		return _firsts.get(s);
	}
	
	public static void main(String[] args) {
		for(Symbol s : Symbol.nonterminals){
			System.out.print(s + " => ");System.out.println(Arrays.toString(get(s).toArray()));
		}
	}
	
}
