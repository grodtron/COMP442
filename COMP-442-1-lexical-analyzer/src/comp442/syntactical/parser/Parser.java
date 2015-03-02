package comp442.syntactical.parser;

import static comp442.syntactical.data.Symbol.EPSILON;
import static comp442.syntactical.data.Symbol.prog;

import java.util.Arrays;

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
	
	public Parser(Scanner s){
		this.scanner = s;
	}
		
	public void parse(){
		ParseTree tree = new ParseTree(prog);
		nextToken();
		parse(tree);
		tree.printSelf();
	}
	
	private void nextToken(){
		token  = scanner.getNext();
		symbol = Symbol.fromToken(token);	
	}
	
	private void parse(ParseTree tree){
		
		// TODO - skip errors
		
		if(tree.symbol.isTerminal){
			if(tree.symbol == symbol){
				
				System.out.println(token);
				
				tree.setToken(token);
				nextToken();
			}else{
				// Big Error!!
				System.err.println("ERROR: unexpected token " + symbol + " expected " + tree.symbol);
				nextToken();
			}
		}else{
			Symbol[][] productions = Grammar.productions.get(tree.symbol);
			for(Symbol[] production : productions){
				if(First.get(production).contains(symbol)){
					// This rule matches !
					for(Symbol child : production){
						ParseTree childTree = new ParseTree(child);
						tree.addChild(childTree);
						parse(childTree);
					}
					return;
				}
				if(First.get(production).contains(EPSILON)
				&& Follow.get(production).contains(symbol)){
					// This rule matches, have to make sure to take the epsilon
					ParseTree epsilonChild = new ParseTree(production[0]);
					epsilonChild.addChild(new ParseTree(EPSILON));
					tree.addChild(epsilonChild);
					for(Symbol child : Arrays.asList(production).subList(1, production.length)){
						ParseTree childTree = new ParseTree(child);
						tree.addChild(childTree);
						parse(childTree);				
					}
					return;
				}
			}
			
			System.err.println("ERROR: no rule matches! ( current symbol is " + tree.symbol + " looking for " + symbol);
		}		
	}
	
}
