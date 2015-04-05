package comp442.lexical.state;

import java.io.IOException;

import comp442.lexical.InvalidCharacterException;
import comp442.lexical.LexableInputStream;
import comp442.lexical.token.Token;
import comp442.lexical.token.AssignmentToken;
import comp442.lexical.token.CloseBraceToken;
import comp442.lexical.token.CloseParenToken;
import comp442.lexical.token.CloseSquareToken;
import comp442.lexical.token.CommaToken;
import comp442.lexical.token.DiamondToken;
import comp442.lexical.token.DotToken;
import comp442.lexical.token.EqualsToken;
import comp442.lexical.token.GreaterThanEqualsToken;
import comp442.lexical.token.GreaterThanToken;
import comp442.lexical.token.LessThanEqualsToken;
import comp442.lexical.token.LessThanToken;
import comp442.lexical.token.MinusToken;
import comp442.lexical.token.OpenBraceToken;
import comp442.lexical.token.OpenParenToken;
import comp442.lexical.token.OpenSquareToken;
import comp442.lexical.token.PlusToken;
import comp442.lexical.token.SemicolonToken;
import comp442.lexical.token.StarToken;
import comp442.lexical.utils.Consumer;
import comp442.lexical.utils.Matcher;

public class Start extends State {

	private boolean done;
	
	private Token token;
	
	public Start(){
		done = false;
	}
	
	@Override
	public State process(LexableInputStream input) throws IOException, InvalidCharacterException {
		token = null;
		done  = false;
		
		Consumer.consumeWhitespace(input);
		
		int c = input.read();
		
		if(Character.isAlphabetic(c)){
			input.unread(c);
			
			for(String word : new String[]{
					"and", "or", "not",
					"if", "else", "then", "for",
					"class",
					"int", "float",
					"get", "put",
					"return", "program"}
			){
				if(Matcher.matchesReservedWord(input, word)){
					return new GotReservedWord(word, input.getLineNumber());
				}
			}

			return new GotAlpha(input.read());
			
		}else
		if(Character.isDigit(c)){
			return new GotDigit(c);
		}else{
			switch(c){
				case '=':
					c = input.read();
					if(c == '='){
						token = new EqualsToken(input.getLineNumber());						
					}else{
						input.unread(c);
						token = new AssignmentToken(input.getLineNumber());
					}
					return this;
				case '<':
					c = input.read();
					switch(c){
						case '=':
							token = new LessThanEqualsToken(input.getLineNumber());
							return this;
						case '>':
							token = new DiamondToken(input.getLineNumber());
							return this;
						default:
							input.unread(c);
							token = new LessThanToken(input.getLineNumber());
							return this;
					}
				case '>':
					c = input.read();
					if(c == '='){
						token = new GreaterThanEqualsToken(input.getLineNumber());						
					}else{
						input.unread(c);
						token = new GreaterThanToken(input.getLineNumber());
					}
					return this;
				case '+':
					token = new PlusToken(input.getLineNumber());
					return this;
				case '-':
					token = new MinusToken(input.getLineNumber());
					return this;
				case '*':
					token = new StarToken(input.getLineNumber());
					return this;
				case '/':
					return new GotSlash();
				case ';':
					token = new SemicolonToken(input.getLineNumber());
					return this;
				case ',':
					token = new CommaToken(input.getLineNumber());
					return this;
				case '.':
					token = new DotToken(input.getLineNumber());
					return this;
				case '(':
					token = new OpenParenToken(input.getLineNumber());
					return this;
				case '{':
					token = new OpenBraceToken(input.getLineNumber());
					return this;
				case '[':
					token = new OpenSquareToken(input.getLineNumber());
					return this;
				case ')':
					token = new CloseParenToken(input.getLineNumber());
					return this;
				case '}':
					token = new CloseBraceToken(input.getLineNumber());
					return this;
				case ']':
					token = new CloseSquareToken(input.getLineNumber());
					return this;
			}
		}
		
		
		if(c == -1){
			done = true;
			return this;
		}else{
			throw new InvalidCharacterException(c, input.getLineNumber());
		}
	}
	
	@Override
	public boolean done(){
		return this.done;
	}
	
	@Override
	public boolean nextTokenReady(){
		return token != null;
	}
	
	@Override
	public Token getToken(){
		return token;
	}

}
