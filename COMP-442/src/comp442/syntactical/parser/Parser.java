package comp442.syntactical.parser;

import static comp442.syntactical.data.Symbol.END_MARKER;
import static comp442.syntactical.data.Symbol.EPSILON;
import static comp442.syntactical.data.Symbol.prog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Set;

import comp442.error.CompilerError;
import comp442.lexical.Scanner;
import comp442.lexical.token.Token;
import comp442.logging.Log;
import comp442.semantic.symboltable.SymbolContext;
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
				
		SymbolContext.reset();
	}
	
	public Parser(File input) throws FileNotFoundException{
		this.scanner = new Scanner(new FileInputStream(input));
				
		nErrors = 0;
		
		SymbolContext.reset();
		
	}

		
	public void parse(){
		ParseTree tree = new ParseTree(prog);
		nextToken();
		parse(tree);
		
		tree.printSelf(Log.getDerivation());
		tree.printParsedCode(Log.getOutput());
		
		Log.getSymbols().print(SymbolContext.printableString());
	}
	
	private void nextToken(){
		previousToken = token;
		token         = scanner.getNext();
		Log.getTokens().println(token);
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
					logError(new CompilerError("Unexpected end of input, expected " + tree.symbol));
				}
				
				return false;
			}
		}else{
			boolean nullable = false;
			Symbol [] nullProduction = null;
			
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
							try {
								child.action.execute(previousToken);
							} catch (CompilerError e) {
								logError(e);
							}
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
					nullProduction = production;
				}
				
			}
			
			if(nullable){
				tree.addChild(new ParseTree(EPSILON));
				
				// We need to make sure semantic actions are not ignored in this case!
				for(Symbol s : nullProduction){
					if(s.isSemanticAction()){
						try{								
							s.action.execute(previousToken);
						}catch(CompilerError e){
							logError(e);
						}
					}
				}
				return true;
			}else{
				//logError(new CompilerError("No rule matches! Current symbol is " + tree.symbol + " looking for " + symbol));
				
				return false;
			}
		}
	}

	private void logError(CompilerError e){
		if(token != null){
			Log.logError("line " + token.lineno + ": " + e.getMessage());
		}else{
			Log.logError("EOF: " + e.getMessage());			
		}
		++nErrors;
	}
	
	public int getNErrors(){
		return nErrors;
	}
	
	private void skipErrors(ParseTree tree) {
		
		Set<Symbol> first = First.get(tree.symbol);
		
		// If this symbol is nullable, we don't care
		if (first.contains(EPSILON)) return;
		
		boolean firstError = true;
		
		// Skip any token that cannot follow the current token
		while( ! first.contains(symbol) && token != null){
			// TODO
			if(firstError){
				logError(new CompilerError("Unexpected token " + symbol + " ('" + token.lexeme + "'), expecting any of " + first));
				firstError = false;
			}
			nextToken();
		}
		
	}
	
}
