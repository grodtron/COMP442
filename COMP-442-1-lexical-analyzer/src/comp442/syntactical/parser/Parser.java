package comp442.syntactical.parser;

import static comp442.syntactical.data.Symbol.END_MARKER;
import static comp442.syntactical.data.Symbol.EPSILON;
import static comp442.syntactical.data.Symbol.prog;

import java.util.Set;

import comp442.lexical.Scanner;
import comp442.lexical.token.Token;
import comp442.syntactical.data.First;
import comp442.syntactical.data.Follow;
import comp442.syntactical.data.Grammar;
import comp442.syntactical.data.Symbol;

public class Parser {

	private Scanner scanner;
	private Token token;
	private Symbol symbol;
	
	private int nErrors;
	
	public Parser(Scanner s){
		this.scanner = s;
		nErrors = 0;
	}
		
	public void parse(){
		ParseTree tree = new ParseTree(prog);
		nextToken();
		parse(tree);
		tree.printSelf();
		tree.printParsedCode();
	}
	
	private void nextToken(){
		token  = scanner.getNext();
		symbol = Symbol.fromToken(token);	
	}
	
	private void parse(ParseTree tree){

		System.out.println("  " + tree.symbol + " -> " + tree.getParent().symbol + "  ("+symbol+")");
		System.out.flush();

		if(tree.symbol.isTerminal){
			if(tree.symbol == symbol){
				
				System.out.println("matched: " + token);
				System.out.flush();
				tree.setToken(token);
				nextToken();
			}else{
				// Big Error!!
				System.err.println("ERROR: " + token);
				System.err.println("ERROR: unexpected token " + symbol + " expected " + tree.symbol);
				System.err.flush();
				skipErrors(tree);
			}
		}else{
			boolean nullable = false;
			Symbol[][] productions = Grammar.productions.get(tree.symbol);
			for(Symbol[] production : productions){
				if(First.get(production).contains(symbol)
				|| (
						First.get(production).contains(EPSILON)
					&&  Follow.get(production).contains(symbol)
				)){
					// This rule matches !
					for(Symbol child : production){
						ParseTree childTree = new ParseTree(child);
						tree.addChild(childTree);
						parse(childTree);
					}
					return;
				}
				// We assume our grammar doesn't include productions with multiple
				// EPSILONs in a row, because that would make no sense ... :P
				if(production[0] == EPSILON){
					nullable = true;
				}
				
			}
			
			if(nullable){
				System.out.println("epsilon'd");
			}else{
				System.err.println("ERROR: no rule matches! Current symbol is " + tree.symbol + " looking for " + symbol);
				System.err.flush();
				skipErrors(tree);
				if(symbol != END_MARKER){
					parse(tree.getParent());
				}
			}
		}		
	}

	public int getNErrors(){
		return nErrors;
	}
	
	private void skipErrors(ParseTree tree) {
		++nErrors;
		
		Symbol A = tree.getParent().symbol;
		Set<Symbol> first = First.get(A);
		
		System.err.println("looking for any of " + first);
		
		// Skip any token that cannot follow the current token
		while( ! first.contains(symbol) && symbol != END_MARKER){
			System.err.println("ERROR: " + token);
			System.err.println("ERROR: impossible token " + symbol + " cannot appear in the context of " + A);
			
			nextToken();
		}
		System.err.flush();
		
	}
	
}
