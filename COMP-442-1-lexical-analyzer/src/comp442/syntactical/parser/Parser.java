package comp442.syntactical.parser;

import static comp442.syntactical.data.Symbol.END_MARKER;
import static comp442.syntactical.data.Symbol.EPSILON;
import static comp442.syntactical.data.Symbol.prog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Set;

import comp442.lexical.Scanner;
import comp442.lexical.token.Token;
import comp442.logging.Log;
import comp442.semantic.action.SemanticContext;
import comp442.syntactical.data.First;
import comp442.syntactical.data.Follow;
import comp442.syntactical.data.Grammar;
import comp442.syntactical.data.Symbol;

public class Parser {

	private Scanner scanner;
	private Token token;
	private Token previousToken;
	private Symbol symbol;
	
	private int nErrors;
		
	public Parser(InputStream in){
		this.scanner = new Scanner(in);
		nErrors = 0;
				
		SemanticContext.reset();
	}
	
	public Parser(File input) throws FileNotFoundException{
		this.scanner = new Scanner(new FileInputStream(input));
		
		String baseName = input.getPath();
		baseName = baseName.substring(0, baseName.lastIndexOf('.'));
		
		Log.error      = new PrintStream(new File(baseName + ".error"));
		Log.output     = new PrintStream(new File(baseName + ".output"));
		Log.derivation = new PrintStream(new File(baseName + ".derivation"));
		Log.symbols    = new PrintStream(new File(baseName + ".symbols"));
		
		nErrors = 0;
		
		SemanticContext.reset();
		
	}

		
	public void parse(){
		ParseTree tree = new ParseTree(prog);
		nextToken();
		parse(tree);
		
		tree.printSelf(Log.derivation);
		tree.printParsedCode(Log.output);
		
		Log.symbols.print(SemanticContext.printableString());
		
		Log.error.close();
		Log.output.close();
		Log.derivation.close();
		Log.symbols.close();
	}
	
	private void nextToken(){
		previousToken = token;
		token         = scanner.getNext();
		symbol        = Symbol.fromToken(token);	
	}
	
	private boolean parse(ParseTree tree){
		
		skipErrors(tree);
		
		if(tree.symbol.isTerminal()){
			if(tree.symbol == symbol){
				
				tree.setToken(token);
				nextToken();
				return true;
			}else{
				if(symbol == END_MARKER){
					Log.error.println("ERROR: unexpected end of input, expected " + tree.symbol);					
				}
				
				return false;
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
						if(child.isSemanticAction()){
							child.action.execute(previousToken);
						}else{
							ParseTree childTree = new ParseTree(child);
							tree.addChild(childTree);
							parse(childTree);
						}
					}
					return true;
				}
				// We assume our grammar doesn't include productions with multiple
				// EPSILONs in a row, because that would make no sense ... :P
				if(production[0] == EPSILON){
					nullable = true;
				}
				
			}
			
			if(nullable){
				tree.addChild(new ParseTree(EPSILON));
				return true;
			}else{
				Log.error.println("ERROR: no rule matches! Current symbol is " + tree.symbol + " looking for " + symbol);
				
				return false;
			}
		}		
	}

	public int getNErrors(){
		return nErrors;
	}
	
	private void skipErrors(ParseTree tree) {
		
		Set<Symbol> first = First.get(tree.symbol);
		
		// If this symbol is nullable, we don't care
		if (first.contains(EPSILON)) return;
		
		// Skip any token that cannot follow the current token
		while( ! first.contains(symbol) && token != null){
			Log.error.println("ERROR: unexpected token '" + symbol + "' on line " + token.lineno + ", expecting any of " + first);			
			++nErrors;
			nextToken();
		}
		
	}
	
}
