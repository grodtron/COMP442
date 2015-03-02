package comp442.syntactical.parser;

import java.util.ArrayList;
import java.util.List;

import comp442.lexical.token.Token;
import comp442.syntactical.data.Symbol;

public class ParseTree {

	public final Symbol symbol;
	
	private final List<ParseTree> children;
	
	private Token token;
	
	public ParseTree(Symbol s){
		symbol = s;
		children = new ArrayList<ParseTree>();
	}

	public void addChild(ParseTree childTree) {
		children.add(childTree);
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
	
	private static class Printer implements Visitor {

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
		this.acceptVisitor(new Printer());
	}
	
}
