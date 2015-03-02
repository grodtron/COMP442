package comp442.syntactical.parser;

import java.util.ArrayList;
import java.util.List;

import comp442.lexical.token.Token;
import comp442.syntactical.data.Symbol;
import static comp442.syntactical.data.Symbol.*;

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
		
		@Override
		public void visit(ParseTree tree) {
			for(int i = 0; i < indentation; ++i){
				System.out.print(' ');
			}
			System.out.println(tree.symbol);
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
	
	public void printSelf(){
		this.acceptVisitor(new DerivationPrinter());
	}
	
	private static class CodePrinter implements Visitor {

		int indentation = 0;
		
		@Override
		public void visit(ParseTree tree) {
			if(tree.symbol.isTerminal && tree.token != null){
				
				if(tree.symbol == tok_open_brace){
					++indentation;
				}else if(tree.symbol == tok_close_brace){
					--indentation;
				}
				
				System.out.print(tree.token.lexeme);
				if(tree.symbol == tok_semicolon || tree.symbol == tok_open_brace || tree.symbol == tok_close_brace){
					System.out.print("\n");
					for(int i = 0; i < indentation; ++i){
						System.out.print("  ");
					}
				}else{
					System.out.print(" ");
				}
				
			}
		}

		@Override
		public void pop() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void push() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public void printParsedCode(){
		this.acceptVisitor(new CodePrinter());
	}
	
}
