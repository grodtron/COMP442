package comp442.syntactical.data;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import comp442.lexical.token.Token;
import comp442.semantic.SemanticAction;
import comp442.semantic.expressions.actions.EndAdditionExpressionAction;
import comp442.semantic.expressions.actions.EndBlockAction;
import comp442.semantic.expressions.actions.EndMultiplicationExpressionAction;
import comp442.semantic.expressions.actions.FinishVariableAction;
import comp442.semantic.expressions.actions.PushAdditionOperationAction;
import comp442.semantic.expressions.actions.PushFloatLiteralAction;
import comp442.semantic.expressions.actions.PushIdentifierAction;
import comp442.semantic.expressions.actions.PushIntLiteralAction;
import comp442.semantic.expressions.actions.PushMultiplicationOperationAction;
import comp442.semantic.expressions.actions.StartAdditionExpressionAction;
import comp442.semantic.expressions.actions.StartAssignmentStatementAction;
import comp442.semantic.expressions.actions.StartBlockAction;
import comp442.semantic.expressions.actions.StartForStatementAction;
import comp442.semantic.expressions.actions.StartIfStatementAction;
import comp442.semantic.expressions.actions.StartMultiplicationExpressionAction;
import comp442.semantic.symboltable.actions.AddFunctionParameterAction;
import comp442.semantic.symboltable.actions.CreateClassAction;
import comp442.semantic.symboltable.actions.CreateFunctionAction;
import comp442.semantic.symboltable.actions.CreateProgramAction;
import comp442.semantic.symboltable.actions.CreateVariableAction;
import comp442.semantic.symboltable.actions.EndScopeAction;
import comp442.semantic.symboltable.actions.NullAction;
import comp442.semantic.symboltable.actions.StartFunctionAction;
import comp442.semantic.symboltable.actions.StoreDimensionAction;
import comp442.semantic.symboltable.actions.StoreIdAction;
import comp442.semantic.symboltable.actions.StoreTypeAction;

public enum Symbol {
	
	prog(Type.Nonterminal),
	classDecl(Type.Nonterminal),
	classBody(Type.Nonterminal),
	classBodyPrime(Type.Nonterminal),
	classBodyVar(Type.Nonterminal),
	classBodyFunc(Type.Nonterminal),
	classBodyFuncPrime(Type.Nonterminal),
	progBody(Type.Nonterminal),
	funcDefs(Type.Nonterminal),
	funcBody(Type.Nonterminal),
	funcBodyVar(Type.Nonterminal),
	funcBodyVarPrime(Type.Nonterminal),
	funcBodyStmt(Type.Nonterminal),
	varDeclArray(Type.Nonterminal),
	controlStat(Type.Nonterminal),
	assignStat(Type.Nonterminal),
	assignExpr(Type.Nonterminal),
	statBlock(Type.Nonterminal),
	statBlockStmts(Type.Nonterminal),
	expr(Type.Nonterminal),
	exprPrime(Type.Nonterminal),
	relExpr(Type.Nonterminal),
	arithExpr(Type.Nonterminal),
	arithExprPrime(Type.Nonterminal),
	sign(Type.Nonterminal),
	term(Type.Nonterminal),
	termPrime(Type.Nonterminal),
	factor(Type.Nonterminal),
	factorIdNest(Type.Nonterminal),
	factorIdNestPrime(Type.Nonterminal),
	factorIdNestPrimePrime(Type.Nonterminal),
	variable(Type.Nonterminal),
	variablePrime(Type.Nonterminal),
	indice(Type.Nonterminal),
	indices(Type.Nonterminal),
	arraySize(Type.Nonterminal),
	type(Type.Nonterminal),
	fParams(Type.Nonterminal),
	fParamsArraySz(Type.Nonterminal),
	fParamsTailStar(Type.Nonterminal),
	fParamsTail(Type.Nonterminal),
	arraySizeStar(Type.Nonterminal),
	aParams(Type.Nonterminal),
	aParamsTailStar(Type.Nonterminal),
	aParamsTail(Type.Nonterminal),
	assignOp(Type.Nonterminal),
	relOp(Type.Nonterminal),
	addOp(Type.Nonterminal),
	multOp(Type.Nonterminal),
	
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////
	
	// reserved words
	tok_and(Type.Terminal),
	tok_or(Type.Terminal),
	tok_not(Type.Terminal),
	
	tok_if(Type.Terminal),
	tok_else(Type.Terminal),
	tok_then(Type.Terminal),
	tok_for(Type.Terminal),
	
	tok_class(Type.Terminal),
	
	tok_int(Type.Terminal),
	tok_float(Type.Terminal),
	
	tok_get(Type.Terminal),
	tok_put(Type.Terminal),
	
	tok_return(Type.Terminal),

	tok_program(Type.Terminal),
	
	// identifier
	tok_id(Type.Terminal),
	
	//
	tok_int_literal(Type.Terminal),
	
	//
	tok_float_literal(Type.Terminal),
	
	//
	tok_equals(Type.Terminal),
	tok_assignment(Type.Terminal),
	tok_greater_than_equals(Type.Terminal),
	tok_greater_than(Type.Terminal),
	tok_less_than_equals(Type.Terminal),
	tok_less_than(Type.Terminal),
	tok_diamond(Type.Terminal),
	tok_plus(Type.Terminal),
	tok_minus(Type.Terminal),
	tok_star(Type.Terminal),
	tok_semicolon(Type.Terminal),
	tok_comma(Type.Terminal),
	tok_dot(Type.Terminal),
	tok_slash(Type.Terminal),
	
	//
	tok_open_paren(Type.Terminal),
	tok_open_brace(Type.Terminal),
	tok_open_square(Type.Terminal),
	tok_close_paren(Type.Terminal),
	tok_close_brace(Type.Terminal),
	tok_close_square(Type.Terminal),
	
	
	EPSILON(Type.Terminal),
	END_MARKER(Type.Terminal),
	
	sym_CreateProgram(new CreateProgramAction()),
	sym_CreateClassScope(new CreateClassAction()),
	sym_StartFunction(new StartFunctionAction()),
	sym_AddFunctionParameter(new AddFunctionParameterAction()),
	sym_CreateFunction(new CreateFunctionAction()),
	sym_StoreType(new StoreTypeAction()),
	sym_StoreId(new StoreIdAction()),
	sym_StoreDimension(new StoreDimensionAction()),
	sym_CreateVariable(new CreateVariableAction()),
	sym_EndScope(new EndScopeAction()),
	
	sem_PushVariableName(new PushIdentifierAction()),
	//sem_PushVariableIndex(new PushIndexAction()),
	sem_FinishVariable(new FinishVariableAction()),
	
	sem_StartAssignmentStatment(new StartAssignmentStatementAction()),
	
	sem_StartAdditionExpression(new StartAdditionExpressionAction()),
	sem_EndAdditionExpression(new EndAdditionExpressionAction()),
	
	sem_StartMultiplicationExpression(new StartMultiplicationExpressionAction()),
	sem_EndMultiplicationExpression(new EndMultiplicationExpressionAction()),
	
	sem_PushIntLiteral(new PushIntLiteralAction()),
	sem_PushFloatLiteral(new PushFloatLiteralAction()),
	
	sem_PushAdditionOperation(new PushAdditionOperationAction()),
	sem_PushMultiplicationOperation(new PushMultiplicationOperationAction()),
	
	sem_StartIfStatement(new StartIfStatementAction()),
	
	sem_StartForStatement(new StartForStatementAction()),
	
	sem_StartBlock(new StartBlockAction()),
	sem_EndBlock(new EndBlockAction()),
	;

	public static enum Type {
		Terminal,
		Nonterminal,
		SemanticAction
	};
	
	
	private final static Set<Symbol> _terminals;
	private final static Set<Symbol> _nonterminals;
	private final static Set<Symbol> _semanticActions;
	
	public final static Set<Symbol> terminals;
	public final static Set<Symbol> nonterminals;
	public final static Set<Symbol> semanticActions;
	
	static {
		_terminals    = new HashSet<Symbol>();
		_nonterminals = new HashSet<Symbol>();
		_semanticActions = new HashSet<Symbol>();
		
		for(Symbol s : Symbol.values()){
			switch(s.symbolType){
				case Terminal:
					_terminals.add(s);
					break;
				case Nonterminal:
					_nonterminals.add(s);
					break;
				case SemanticAction:
					_semanticActions.add(s);
					break;
				default:
					break;
			}
		}
		
		terminals       = Collections.unmodifiableSet(_terminals);
		nonterminals    = Collections.unmodifiableSet(_nonterminals);
		semanticActions = Collections.unmodifiableSet(_semanticActions);
	}
	
	public final Type symbolType;
	public final SemanticAction action;
	Symbol(Type type){
		this.action = NullAction.instance;
		this.symbolType = type;
	}
	
	Symbol(SemanticAction action){
		this.action = action;
		this.symbolType = Type.SemanticAction;
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
		case tok_float_literal:			return "float_literal";
		case tok_for:					return "for";
		case tok_get:					return "get";
		case tok_greater_than:			return ">";
		case tok_greater_than_equals:	return ">=";
		case tok_id:					return "id";
		case tok_if:					return "if";
		case tok_int:					return "int";
		case tok_int_literal:			return "int_literal";
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
		switch(symbolType){
		case Nonterminal:
			return "&lt;" + name() + "&gt;";
		case SemanticAction:
			// Just to have a visual difference between symbol-table related
			// actions and AST related actions, we'll give them a different color
			if(name().startsWith("sym")){
				return "<span style='color:red'>#" + name() + "#</span>";
			}else{
				return "<span style='color:blue'>@" + name() + "@</span>";
			}
		case Terminal:
			return "<b>" + terminalToString()
					.replace("<", "&lt;")
					.replace(">", "&gt;") +
			"</b>";
		default:
			return "<b>==========UNKNOWN==========</b>";
		}
	}

	public boolean isTerminal() {
		return symbolType == Type.Terminal;
	}

	public boolean isNonterminal() {
		return symbolType == Type.Nonterminal;
	}

	public boolean isSemanticAction() {
		return symbolType == Type.SemanticAction;
	}

}
