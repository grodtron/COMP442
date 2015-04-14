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
		
		String baseName = input.getPath();
		baseName = baseName.substring(0, baseName.lastIndexOf('.'));
		
		Log.error      = new PrintStream(new File(baseName + ".error"));
		Log.output     = new PrintStream(new File(baseName + ".output"));
		Log.derivation = new PrintStream(new File(baseName + ".derivation"));
		Log.symbols    = new PrintStream(new File(baseName + ".symbols"));
		
		// The actual ASM file
		Log.masm       = new PrintStream(new File(baseName + ".masm"));
				
		nErrors = 0;
		
		SymbolContext.reset();
		
	}

		
	public void parse(){
		ParseTree tree = new ParseTree(prog);
		nextToken();
		parse(tree);
		
		tree.printSelf(Log.derivation);
		tree.printParsedCode(Log.output);
		
		Log.symbols.print(SymbolContext.printableString());
		
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
								e.printStackTrace(System.err);
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
							e.printStackTrace(System.err);
						}
					}
				}
				return true;
			}else{
				logError(new CompilerError("No rule matches! Current symbol is " + tree.symbol + " looking for " + symbol));
				
				return false;
			}
		}
	}

	private void logError(CompilerError e){
		Log.error.println("line " + token.lineno + ": " + e.getMessage());
		++nErrors;
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
			// TODO
			logError(new CompilerError("ERROR: unexpected token '" + symbol + "', expecting any of " + first));
			nextToken();
		}
		
	}
	
}
