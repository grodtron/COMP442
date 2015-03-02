package comp442.syntactical.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import comp442.lexical.token.Token;

public enum Symbol {
	
	prog(false),
	classDecl(false),
	classBodyVar(false),
	classBodyFunc(false),
	progBody(false),
	funcDefs(false),
	funcHead(false),
	funcDef(false),
	funcBody(false),
	funcBodyVar(false),
	funcBodyStmt(false),
	varDecl(false),
	varDeclArray(false),
	statement(false),
	assignStat(false),
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
			return EPSILON;
		}else{
			// TODO dirty hack
			return Symbol.valueOf(t.token.name());
		}
	}

}
