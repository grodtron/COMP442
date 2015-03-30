package comp442.syntactical.parser;

import static comp442.syntactical.data.Symbol.tok_close_brace;
import static comp442.syntactical.data.Symbol.tok_open_brace;
import static comp442.syntactical.data.Symbol.tok_semicolon;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import comp442.lexical.token.Token;
import comp442.syntactical.data.Symbol;

public class ParseTree {

	public final Symbol symbol;
	
	private final List<ParseTree> children;
	private ParseTree parent;
	
	private Token token;
	
	public ParseTree(Symbol s){
		symbol = s;
		children = new ArrayList<ParseTree>();
		parent = this;
	}

	public void addChild(ParseTree childTree) {
		children.add(childTree);
		childTree.parent = this;
	}

	public ParseTree getParent() {
		return parent;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public Token getToken() {
		return token;
	}
	
	public static interface Visitor {
		public void visit(ParseTree tree);
		public void pop();
		public void push();
	}
	
	public void acceptVisitor(Visitor v){
		v.visit(this);
		v.push();
		for(ParseTree child : children){
			child.acceptVisitor(v);
		}
		v.pop();
	}
	
	private static class DerivationPrinter implements Visitor {

		private int indentation = 0;
		private PrintStream output;
		
		public DerivationPrinter(PrintStream output) {
			this.output = output;
		}

		@Override
		public void visit(ParseTree tree) {
			for(int i = 0; i < indentation; ++i){
				output.print(' ');
			}
			output.println(tree.symbol);
		}

		@Override
		public void pop() {
			--indentation;
		}

		@Override
		public void push() {
			++indentation;
		}
	}
	
	public void printSelf(PrintStream derivation){
		this.acceptVisitor(new DerivationPrinter(derivation));
	}
	
	private static class CodePrinter implements Visitor {

		int indentation = 0;
		
		private PrintStream output;
		
		public CodePrinter(PrintStream output) {
			this.output = output;
		}

		@Override
		public void visit(ParseTree tree) {
			if(tree.symbol.isTerminal && tree.token != null){
				
				if(tree.symbol == tok_open_brace){
					++indentation;
				}else if(tree.symbol == tok_close_brace){
					--indentation;
				}
				
				output.print(tree.token.lexeme);
				if(tree.symbol == tok_semicolon || tree.symbol == tok_open_brace){
					output.print("\n");
					for(int i = 0; i < indentation; ++i){
						output.print("  ");
					}
				}else{
					output.print(" ");
				}
				
			}
		}

		@Override
		public void pop() {}

		@Override
		public void push() {}
		
	}
	
	public void printParsedCode(PrintStream output){
		this.acceptVisitor(new CodePrinter(output));
	}
	
}
