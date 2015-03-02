package comp442.syntactical.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import comp442.lexical.token.Token;

public enum Symbol {
	
	prog(false),
	classDecl(false),
	classBodyVar(false),
	classBodyVarPrime(false),
	classBodyFunc(false),
	progBody(false),
	funcDefs(false),
	funcBody(false),
	funcBodyVar(false),
	funcBodyVarPrime(false),
	funcBodyStmt(false),
	varDeclArray(false),
	controlStat(false),
	assignStat(false),
	assignExpr(false),
	statBlock(false),
	statBlockStmts(false),
	expr(false),
	exprPrime(false),
	relExpr(false),
	arithExpr(false),
	arithExprPrime(false),
	sign(false),
	term(false),
	termPrime(false),
	factor(false),
	factorIdNest(false),
	factorIdNestPrime(false),
	factorIdNestPrimePrime(false),
	variable(false),
	variablePrime(false),
	indice(false),
	indices(false),
	arraySize(false),
	type(false),
	fParams(false),
	fParamsArraySz(false),
	fParamsTailStar(false),
	fParamsTail(false),
	arraySizeStar(false),
	aParams(false),
	aParamsTailStar(false),
	aParamsTail(false),
	assignOp(false),
	relOp(false),
	addOp(false),
	multOp(false),
	
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	
	// reserved words
	tok_and(true),
	tok_or(true),
	tok_not(true),
	
	tok_if(true),
	tok_else(true),
	tok_then(true),
	tok_for(true),
	
	tok_class(true),
	
	tok_int(true),
	tok_float(true),
	
	tok_get(true),
	tok_put(true),
	
	tok_return(true),

	tok_program(true),
	
	// identifier
	tok_id(true),
	
	//
	tok_int_literal(true),
	
	//
	tok_float_literal(true),
	
	//
	tok_equals(true),
	tok_assignment(true),
	tok_greater_than_equals(true),
	tok_greater_than(true),
	tok_less_than_equals(true),
	tok_less_than(true),
	tok_diamond(true),
	tok_plus(true),
	tok_minus(true),
	tok_star(true),
	tok_semicolon(true),
	tok_comma(true),
	tok_dot(true),
	tok_slash(true),
	
	//
	tok_open_paren(true),
	tok_open_brace(true),
	tok_open_square(true),
	tok_close_paren(true),
	tok_close_brace(true),
	tok_close_square(true),
	
	
	EPSILON(true),
	END_MARKER(true),
	;
		
	private final static Set<Symbol> _terminals;
	private final static Set<Symbol> _nonterminals;
	
	public final static Set<Symbol> terminals;
	public final static Set<Symbol> nonterminals;
	
	static {
		_terminals    = new HashSet<Symbol>();
		_nonterminals = new HashSet<Symbol>();
		
		for(Symbol s : Symbol.values()){
			if(s.isTerminal){
				_terminals.add(s);
			}else{
				_nonterminals.add(s);
			}
		}
		
		terminals     = Collections.unmodifiableSet(_terminals);
		nonterminals  = Collections.unmodifiableSet(_nonterminals);
	}
	
	public final boolean isTerminal;
	Symbol(boolean isTerminal){
		this.isTerminal = isTerminal;
	}
	public static Symbol fromToken(Token t) {
		if(t == null){
			return END_MARKER;
		}else{
			// TODO dirty hack
			return Symbol.valueOf(t.token.name());
		}
	}
	
	private String terminalToString(){
		switch(this){
		case END_MARKER: 				return "$";
		case EPSILON:    	 			return "&#949;";
		case tok_and:    	 			return "and";
		case tok_assignment: 			return "=";
		case tok_class:      			return "class";
		case tok_close_brace:			return "}";
		case tok_close_paren:			return ")";
		case tok_close_square:			return "]";
		case tok_comma:					return ",";
		case tok_diamond:				return "<>";
		case tok_dot:					return ".";
		case tok_else:					return "else";
		case tok_equals:				return "==";
		case tok_float:					return "float";
		case tok_float_literal:			return "<float>";
		case tok_for:					return "for";
		case tok_get:					return "get";
		case tok_greater_than:			return ">";
		case tok_greater_than_equals:	return ">=";
		case tok_id:					return "id";
		case tok_if:					return "if";
		case tok_int:					return "int";
		case tok_int_literal:			return "<int>";
		case tok_less_than:				return "<";
		case tok_less_than_equals:		return "<=";
		case tok_minus:					return "-";
		case tok_not:					return "not";
		case tok_open_brace:			return "{";
		case tok_open_paren:			return "(";
		case tok_open_square:			return "[";
		case tok_or:					return "or";
		case tok_plus:					return "+";
		case tok_program:				return "program";
		case tok_put:					return "put";
		case tok_return:				return "return";
		case tok_semicolon:				return ";";
		case tok_slash:					return "/";
		case tok_star:					return "*";
		case tok_then:					return "then";
		default:						return "NONTERMINAL";
		}
	}
	
	public String toHtmlString(){
		if(isTerminal){
			return "<b>" + terminalToString()
							.replace("<", "&lt;")
							.replace(">", "&gt;") +
					"</b>";
		}else{
			return name();
		}
	}

}
