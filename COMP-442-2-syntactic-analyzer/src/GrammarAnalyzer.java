import java.util.HashMap;
import java.util.Map;

import symbols.Symbol;
import static symbols.Symbol.*;

public class GrammarAnalyzer {

	
	private static Map<Symbol, Symbol[][] > productions;
	
	static {
		
		productions = new HashMap<Symbol, Symbol[][]>();
		
		productions.put(prog, new Symbol[][]{ new Symbol[]{	classDecl, prog, }, new Symbol[]{ progBody,}});
		productions.put(classDecl, new Symbol[][]{ new Symbol[]{	tok_class, tok_id, tok_open_brace, classBodyVar,}});
		productions.put(classBodyVar, new Symbol[][]{ new Symbol[]{	varDecl, classBodyVar, }, new Symbol[]{ classBodyFunc,}});
		productions.put(classBodyFunc, new Symbol[][]{ new Symbol[]{	funcDef, classBodyFunc, }, new Symbol[]{ tok_close_brace,  tok_semicolon }});
		productions.put(progBody, new Symbol[][]{ new Symbol[]{	tok_program, funcBody,  tok_semicolon,  funcDefs,}});
		productions.put(funcDefs, new Symbol[][]{ new Symbol[]{	funcDef, funcDefs, }, new Symbol[]{ EPSILON}});
		productions.put(funcHead, new Symbol[][]{ new Symbol[]{	type, tok_id,  tok_open_paren,  fParams,  tok_close_paren }});
		productions.put(funcDef, new Symbol[][]{ new Symbol[]{	funcHead,funcBody, tok_semicolon }});
		productions.put(funcBody, new Symbol[][]{ new Symbol[]{	tok_open_brace, funcBodyVar,}});
		productions.put(funcBodyVar, new Symbol[][]{ new Symbol[]{	varDecl, funcBodyVar, }, new Symbol[]{ funcBodyStmt,}});
		productions.put(funcBodyStmt, new Symbol[][]{ new Symbol[]{	statement, funcBodyStmt, }, new Symbol[]{ tok_close_brace}});
		productions.put(varDecl, new Symbol[][]{ new Symbol[]{	type, tok_id, varDeclArray,}});
		productions.put(varDeclArray, new Symbol[][]{ new Symbol[]{	arraySize, varDeclArray, }, new Symbol[]{  tok_semicolon }});
		productions.put(statement, new Symbol[][]{ new Symbol[]{	assignStat,  tok_semicolon 
			}, new Symbol[]{	tok_if,  tok_open_paren,  expr,  tok_close_paren,  tok_then, statBlock, tok_else, statBlock,  tok_semicolon 
			}, new Symbol[]{	tok_for, tok_open_paren, type,tok_id,assignOp,expr, tok_semicolon, relExpr, tok_semicolon, assignStat, tok_close_paren, statBlock, tok_semicolon 
			}, new Symbol[]{	tok_get, tok_open_paren, variable, tok_close_paren,  tok_semicolon 
			}, new Symbol[]{	tok_put, tok_open_paren, expr, tok_close_paren,  tok_semicolon 
			}, new Symbol[]{	tok_return, tok_open_paren, expr, tok_close_paren,  tok_semicolon }});
		productions.put(assignStat, new Symbol[][]{ new Symbol[]{	variable, assignOp, expr,}});
		productions.put(statBlock, new Symbol[][]{ new Symbol[]{	tok_open_brace, statBlockStmts, }, new Symbol[]{ statement, }, new Symbol[]{ EPSILON}});
		productions.put(statBlockStmts, new Symbol[][]{ new Symbol[]{	statement, statBlockStmts, }, new Symbol[]{ tok_close_brace}});
		productions.put(expr, new Symbol[][]{ new Symbol[]{	arithExpr, }, new Symbol[]{ relExpr,}});
		productions.put(relExpr, new Symbol[][]{ new Symbol[]{	arithExpr,relOp,arithExpr,}});
		productions.put(arithExpr, new Symbol[][]{ new Symbol[]{	term,arithExprPrime,}});
		productions.put(arithExprPrime, new Symbol[][]{ new Symbol[]{	addOp,term,arithExprPrime, }, new Symbol[]{ EPSILON}});
		productions.put(sign, new Symbol[][]{ new Symbol[]{	tok_plus }, new Symbol[]{ tok_minus }});
		productions.put(term, new Symbol[][]{ new Symbol[]{	factor,termPrime,}});
		productions.put(termPrime, new Symbol[][]{ new Symbol[]{	multOp,factor,termPrime, }, new Symbol[]{ EPSILON}});
		productions.put(factor, new Symbol[][]{ new Symbol[]{	variable,
			}, new Symbol[]{	factorIdNest,
			}, new Symbol[]{	num
			}, new Symbol[]{	 tok_open_paren, arithExpr, tok_close_paren 
			}, new Symbol[]{	tok_not, factor,
			}, new Symbol[]{	sign, factor}});
		productions.put(factorIdNest, new Symbol[][]{ new Symbol[]{	idNest, factorIdNest, }, new Symbol[]{ tok_id , tok_open_paren,  aParams,  tok_close_paren }});
		productions.put(idNest, new Symbol[][]{ new Symbol[]{	id, idNestIndices,}});
		productions.put(idNestIndices, new Symbol[][]{ new Symbol[]{	indice, idNestIndices, }, new Symbol[]{  tok_dot }});
		productions.put(indice, new Symbol[][]{ new Symbol[]{	tok_open_square, arithExpr, tok_close_square}});
		productions.put(arraySize, new Symbol[][]{ new Symbol[]{	tok_open_square, tok_int, tok_close_square}});
		productions.put(type, new Symbol[][]{ new Symbol[]{	tok_int }, new Symbol[]{ tok_float }, new Symbol[]{ tok_id}});
		productions.put(fParams, new Symbol[][]{ new Symbol[]{	type, tok_id, fParamsArraySz, }, new Symbol[]{ EPSILON}});
		productions.put(fParamsArraySz, new Symbol[][]{ new Symbol[]{	arraySize, fParamsArraySz, }, new Symbol[]{ fParamsTailStar,}});
		productions.put(fParamsTailStar, new Symbol[][]{ new Symbol[]{	fParamsTail, fParamsTailStar, }, new Symbol[]{ EPSILON}});
		productions.put(fParamsTail, new Symbol[][]{ new Symbol[]{	tok_comma, type, tok_id, arraySizeStar,}});
		productions.put(arraySizeStar, new Symbol[][]{ new Symbol[]{	arraySize, arraySizeStar, }, new Symbol[]{ EPSILON}});
		productions.put(aParams, new Symbol[][]{ new Symbol[]{	expr, aParamsTailStar, }, new Symbol[]{ EPSILON}});
		productions.put(aParamsTailStar, new Symbol[][]{ new Symbol[]{	aParamsTail, aParamsTailStar, }, new Symbol[]{ EPSILON}});
		productions.put(aParamsTail, new Symbol[][]{ new Symbol[]{	tok_comma, expr,}});
		productions.put(assignOp, new Symbol[][]{ new Symbol[]{	tok_assignment }});
//		productions.put(relOp, new Symbol[][]{ new Symbol[]{	== }, new Symbol[]{ <> }, new Symbol[]{ < }, new Symbol[]{ > }, new Symbol[]{ <= }, new Symbol[]{ >=}});
//		productions.put(addOp, new Symbol[][]{ new Symbol[]{	+ }, new Symbol[]{ - }, new Symbol[]{ or}});
//		productions.put(multOp, new Symbol[][]{ new Symbol[]{	* }, new Symbol[]{ / }, new Symbol[]{ and}});
		
	}
	
	
}
