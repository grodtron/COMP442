package comp442.syntactical.parser;

import static comp442.syntactical.data.Symbol.tok_close_brace;
import static comp442.syntactical.data.Symbol.tok_open_brace;
import static comp442.syntactical.data.Symbol.tok_semicolon;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import comp442.lexical.token.Token;
import comp442.syntactical.data.Symbol;
import comp442.utils.Visitable;
import comp442.utils.Visitor;
import comp442.utils.VisitorAcceptor;

public class ParseTree implements Visitable<ParseTree>{

	public final Symbol symbol;
	
	private final List<ParseTree> children;
	private ParseTree parent;
	
	private Token token;
	
	private VisitorAcceptor<ParseTree> visitorAcceptor;
	
	public ParseTree(Symbol s){
		symbol = s;
		children = new ArrayList<ParseTree>();
		parent = this;
		visitorAcceptor = new VisitorAcceptor<ParseTree>(this, children);
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
	
	@Override
	public void acceptVisitor(Visitor<ParseTree> visitor) {
		visitorAcceptor.acceptVisitor(visitor);
	}

	
	private static class DerivationPrinter implements Visitor<ParseTree> {

		private int indentation = 0;
		private PrintStream output;
		
		public DerivationPrinter(PrintStream output) {
			this.output = output;
		}

		@Override
		public void visit(Visitable<ParseTree> tree) {
			for(int i = 0; i < indentation; ++i){
				output.print(' ');
			}
			output.println(tree.get().symbol);
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
	
	private static class CodePrinter implements Visitor<ParseTree> {

		int indentation = 0;
		
		private PrintStream output;
		
		public CodePrinter(PrintStream output) {
			this.output = output;
		}

		@Override
		public void visit(Visitable<ParseTree> tree) {
			if(tree.get().symbol.isTerminal() && tree.get().token != null){
				
				if(tree.get().symbol == tok_open_brace){
					++indentation;
				}else if(tree.get().symbol == tok_close_brace){
					--indentation;
				}
				
				output.print(tree.get().token.lexeme);
				if(tree.get().symbol == tok_semicolon || tree.get().symbol == tok_open_brace){
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

	@Override
	public ParseTree get() {
		return this;
	}
	
}
