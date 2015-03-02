package comp442.syntactical.data;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static comp442.syntactical.data.Symbol.*;

public class Grammar {

	// Static
	private Grammar(){}
	
	public final static Map<Symbol, Symbol[][] > productions;
	
	static {		
		Map<Symbol, Symbol[][] > p = new HashMap < Symbol, Symbol[][] > ();

		p.put(prog, new Symbol[][] {
			new Symbol[] {
				classDecl, prog,
			}, new Symbol[] {
				progBody,
			}
		});
		p.put(classDecl, new Symbol[][] {
			new Symbol[] {
				tok_class, tok_id, tok_open_brace, classBodyVar,
			}
		});
		p.put(classBodyVar, new Symbol[][] {
			new Symbol[] {
				varDecl, classBodyVar,
			}, new Symbol[] {
				classBodyFunc,
			}
		});
		p.put(classBodyFunc, new Symbol[][] {
			new Symbol[] {
				funcDef, classBodyFunc,
			}, new Symbol[] {
				tok_close_brace, tok_semicolon
			}
		});
		p.put(progBody, new Symbol[][] {
			new Symbol[] {
				tok_program, funcBody, tok_semicolon, funcDefs,
			}
		});
		p.put(funcDefs, new Symbol[][] {
			new Symbol[] {
				funcDef, funcDefs,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(funcHead, new Symbol[][] {
			new Symbol[] {
				type, tok_id, tok_open_paren, fParams, tok_close_paren
			}
		});
		p.put(funcDef, new Symbol[][] {
			new Symbol[] {
				funcHead, funcBody, tok_semicolon
			}
		});
		p.put(funcBody, new Symbol[][] {
			new Symbol[] {
				tok_open_brace, funcBodyVar,
			}
		});
		p.put(funcBodyVar, new Symbol[][] {
			new Symbol[] {
				varDecl, funcBodyVar,
			}, new Symbol[] {
				funcBodyStmt,
			}
		});
		p.put(funcBodyStmt, new Symbol[][] {
			new Symbol[] {
				statement, funcBodyStmt,
			}, new Symbol[] {
				tok_close_brace
			}
		});
		p.put(varDecl, new Symbol[][] {
			new Symbol[] {
				type, tok_id, varDeclArray,
			}
		});
		p.put(varDeclArray, new Symbol[][] {
			new Symbol[] {
				arraySize, varDeclArray,
			}, new Symbol[] {
				tok_semicolon
			}
		});
		p.put(statement, new Symbol[][] {
			new Symbol[] {
				assignStat, tok_semicolon
			}, new Symbol[] {
				tok_if, tok_open_paren, expr, tok_close_paren, tok_then, statBlock, tok_else, statBlock, tok_semicolon
			}, new Symbol[] {
				tok_for, tok_open_paren, type, tok_id, assignOp, expr, tok_semicolon, relExpr, tok_semicolon, assignStat, tok_close_paren, statBlock, tok_semicolon
			}, new Symbol[] {
				tok_get, tok_open_paren, variable, tok_close_paren, tok_semicolon
			}, new Symbol[] {
				tok_put, tok_open_paren, expr, tok_close_paren, tok_semicolon
			}, new Symbol[] {
				tok_return, tok_open_paren, expr, tok_close_paren, tok_semicolon
			}
		});
		p.put(assignStat, new Symbol[][] {
			new Symbol[] {
				variable, assignOp, expr,
			}
		});
		p.put(statBlock, new Symbol[][] {
			new Symbol[] {
				tok_open_brace, statBlockStmts,
			}, new Symbol[] {
				statement,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(statBlockStmts, new Symbol[][] {
			new Symbol[] {
				statement, statBlockStmts,
			}, new Symbol[] {
				tok_close_brace
			}
		});
		p.put(expr, new Symbol[][] {
			new Symbol[] {
				term, exprPrime
			}
		});
		p.put(exprPrime, new Symbol[][] {
			new Symbol[] {
				relOp, expr
			},
			new Symbol[] {
				addOp, expr
			},
			new Symbol[] {
				EPSILON
			},
		});
		p.put(relExpr, new Symbol[][] {
			new Symbol[] {
				arithExpr, relOp, arithExpr,
			}
		});
		p.put(arithExpr, new Symbol[][] {
			new Symbol[] {
				term, arithExprPrime,
			}
		});
		p.put(arithExprPrime, new Symbol[][] {
			new Symbol[] {
				addOp, arithExpr,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(sign, new Symbol[][] {
			new Symbol[] {
				tok_plus
			}, new Symbol[] {
				tok_minus
			}
		});
		p.put(term, new Symbol[][] {
			new Symbol[] {
				factor, termPrime,
			}
		});
		p.put(termPrime, new Symbol[][] {
			new Symbol[] {
				multOp, factor, termPrime,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(factor, new Symbol[][] {
			new Symbol[] {
				factorIdNest,
			}, new Symbol[] {
				tok_int_literal
			}, new Symbol[] {
				tok_float_literal
			}, new Symbol[] {
				tok_open_paren, arithExpr, tok_close_paren
			}, new Symbol[] {
				tok_not, factor,
			}, new Symbol[] {
				sign, factor
			}
		});
		p.put(factorIdNest, new Symbol[][] {
			new Symbol[] {
				tok_id, factorIdNestPrime,
			}
		});
		p.put(factorIdNestPrime, new Symbol[][] {
			new Symbol[] {
				indices, factorIdNestPrimePrime,
			}, new Symbol[] {
				tok_open_paren, aParams, tok_close_paren
			}
		});
		p.put(factorIdNestPrimePrime, new Symbol[][] {
			new Symbol[] {
				tok_dot, factorIdNest,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(variable, new Symbol[][] {
			new Symbol[] {
				tok_id, indices, variablePrime
			}
		});
		p.put(variablePrime, new Symbol[][] {
			new Symbol[] {
				tok_dot, variable
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(indices, new Symbol[][] {
			new Symbol[] {
				indice, indices
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(indice, new Symbol[][] {
			new Symbol[] {
				tok_open_square, arithExpr, tok_close_square
			}
		});
		p.put(arraySize, new Symbol[][] {
			new Symbol[] {
				tok_open_square, tok_int_literal, tok_close_square
			}
		});
		p.put(type, new Symbol[][] {
			new Symbol[] {
				tok_int
			}, new Symbol[] {
				tok_float
			}, new Symbol[] {
				tok_id
			}
		});
		p.put(fParams, new Symbol[][] {
			new Symbol[] {
				type, tok_id, fParamsArraySz,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(fParamsArraySz, new Symbol[][] {
			new Symbol[] {
				arraySize, fParamsArraySz,
			}, new Symbol[] {
				fParamsTailStar,
			}
		});
		p.put(fParamsTailStar, new Symbol[][] {
			new Symbol[] {
				fParamsTail, fParamsTailStar,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(fParamsTail, new Symbol[][] {
			new Symbol[] {
				tok_comma, type, tok_id, arraySizeStar,
			}
		});
		p.put(arraySizeStar, new Symbol[][] {
			new Symbol[] {
				arraySize, arraySizeStar,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(aParams, new Symbol[][] {
			new Symbol[] {
				expr, aParamsTailStar,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(aParamsTailStar, new Symbol[][] {
			new Symbol[] {
				aParamsTail, aParamsTailStar,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(aParamsTail, new Symbol[][] {
			new Symbol[] {
				tok_comma, expr,
			}
		});
		p.put(assignOp, new Symbol[][] {
			new Symbol[] {
				tok_assignment
			}
		});
		p.put(relOp, new Symbol[][] {
			new Symbol[] {
				tok_equals
			}, new Symbol[] {
				tok_diamond
			}, new Symbol[] {
				tok_less_than
			}, new Symbol[] {
				tok_greater_than
			}, new Symbol[] {
				tok_less_than_equals
			}, new Symbol[] {
				tok_greater_than_equals
			}
		});
		p.put(addOp, new Symbol[][] {
			new Symbol[] {
				tok_plus
			}, new Symbol[] {
				tok_minus
			}, new Symbol[] {
				tok_or
			}
		});
		p.put(multOp, new Symbol[][] {
			new Symbol[] {
				tok_star
			}, new Symbol[] {
				tok_slash
			}, new Symbol[] {
				tok_and
			}
		});
		
		productions = Collections.unmodifiableMap(p);
		
	}
	
	private static void printAmbiguities(){
		for(Symbol s : Symbol.nonterminals){
			Symbol [][] productions = Grammar.productions.get(s);
			for(int i = 0; i < productions.length; ++i){
				for(int j = 0; j < i; ++j){
					Set<Symbol> intersection = new HashSet<Symbol>(First.get(productions[i]));
					intersection.retainAll(First.get(productions[j]));
					if(intersection.size() > 0){
						System.out.println("Ambiguity in " + s + " for productions " + i + " and " + j);
						System.out.println(" symbols: " + intersection);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		printAmbiguities();
	}
	
}
