package comp442.syntactical.data;
import static comp442.syntactical.data.Symbol.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
				tok_class, tok_id, sym_CreateClassScope, tok_open_brace, classBody,
			}
		});
		p.put(classBody, new Symbol[][] {
			new Symbol[] {
				type, sym_StoreType, tok_id, sym_StoreId, classBodyPrime,
			},
			new Symbol[] {
				tok_close_brace, tok_semicolon, sym_EndScope
			},		
		});
		p.put(classBodyPrime, new Symbol[][] {
			new Symbol[] {
				classBodyVar,
			},
			new Symbol[] {
				classBodyFunc,
			},		
		});
		p.put(classBodyVar, new Symbol[][] {
			new Symbol[] {
				varDeclArray, sym_CreateVariable, classBody,
			}
		});
		p.put(classBodyFunc, new Symbol[][] {
			new Symbol[] {
				tok_open_paren, sym_StartMemberFunction, fParams, tok_close_paren, sym_CreateFunction, funcBody, tok_semicolon, classBodyFuncPrime,
			}
		});
		p.put(classBodyFuncPrime, new Symbol[][] {
			new Symbol[] {
				type, sym_StoreType, tok_id, sym_StoreId, classBodyFunc,
			}, new Symbol[] {
				tok_close_brace, tok_semicolon, sym_EndScope /* end class scope */
			}
		});
		
		p.put(progBody, new Symbol[][] {
			new Symbol[] {
				tok_program, sym_CreateProgram, funcBody, tok_semicolon, funcDefs,
			}
		});
		p.put(funcDefs, new Symbol[][] {
			new Symbol[] {
					type, sym_StoreType, tok_id, sym_StoreId, tok_open_paren, sym_StartFunction, fParams, tok_close_paren, sym_CreateFunction, funcBody, tok_semicolon, funcDefs,
			}, new Symbol[] {
					EPSILON
				}
		});
		p.put(funcBody, new Symbol[][] {
			new Symbol[] {
				tok_open_brace, funcBodyVar,
			}
		});
		p.put(funcBodyVar, new Symbol[][] {
			new Symbol[] {
				tok_int, sym_StoreType, tok_id, sym_StoreId, varDeclArray, sym_CreateVariable, funcBodyVar,
			}, new Symbol[] {
				tok_float, sym_StoreType, tok_id, sym_StoreId, varDeclArray, sym_CreateVariable, funcBodyVar,
			}, new Symbol[] {
				tok_id, funcBodyVarPrime
			}, new Symbol[] {
				controlStat, funcBodyStmt,
			}, new Symbol[] {
				tok_close_brace, sym_EndScope,
			}
		});
		p.put(funcBodyVarPrime, new Symbol[][] {
			new Symbol[] {
				sym_StoreType, tok_id, sym_StoreId, varDeclArray, sym_CreateVariable, funcBodyVar,
			}, new Symbol[] {
				sem_StartAssignmentStatment, sem_PushVariableName, indices, variablePrime, assignStat, funcBodyStmt,
			}
		});
		p.put(funcBodyStmt, new Symbol[][] {
			new Symbol[] {
				sem_StartAssignmentStatment, variable, assignStat, funcBodyStmt,
			}, new Symbol[] {
				controlStat, funcBodyStmt,
			}, new Symbol[] {
				tok_close_brace, sym_EndScope
			}
		});
		p.put(varDeclArray, new Symbol[][] {
			new Symbol[] {
				arraySize, varDeclArray,
			}, new Symbol[] {
				tok_semicolon
			}
		});
		p.put(controlStat, new Symbol[][] {
			new Symbol[] {
				tok_if, sem_StartIfStatement, tok_open_paren, expr, tok_close_paren, tok_then, statBlock, tok_else, statBlock, tok_semicolon
			}, new Symbol[] {
				tok_for, sem_StartForStatement, tok_open_paren, type, sym_StoreType, sem_StartAssignmentStatment, tok_id, sym_StoreId, sym_CreateVariable, sem_PushVariableName, sem_FinishVariable, assignOp, expr, tok_semicolon, relExpr, tok_semicolon, sem_StartAssignmentStatment, variable, assignExpr, tok_close_paren, statBlock, tok_semicolon
			}, new Symbol[] {
				tok_get, sem_StartGetStatement, tok_open_paren, variable, tok_close_paren, tok_semicolon
			}, new Symbol[] {
				tok_put, sem_StartPutStatement, tok_open_paren, expr, tok_close_paren, tok_semicolon
			}, new Symbol[] {
				tok_return, sem_StartReturnStatement, tok_open_paren, expr, tok_close_paren, tok_semicolon
			}
		});
		p.put(assignStat, new Symbol[][] {
			new Symbol[] {
				assignExpr, tok_semicolon
			},
		});
		p.put(assignExpr, new Symbol[][] {
			new Symbol[] {
				assignOp, expr,
			}
		});
		p.put(statBlock, new Symbol[][] {
			new Symbol[] {
				tok_open_brace, sem_StartBlock, statBlockStmts,
			}, new Symbol[] {
				sem_StartBlock, sem_StartAssignmentStatment, variable, assignStat, sem_EndBlock
			}, new Symbol[] {
				sem_StartBlock, controlStat, sem_EndBlock
			}, new Symbol[] {
				EPSILON, sem_StartBlock, sem_EndBlock
			}
		});
		p.put(statBlockStmts, new Symbol[][] {
			new Symbol[] {
				sem_StartAssignmentStatment, variable, assignStat, statBlockStmts,
			},
			new Symbol[] {
				controlStat, statBlockStmts,
			}, new Symbol[] {
				tok_close_brace, sem_EndBlock
			}
		});
		p.put(expr, new Symbol[][] {
			new Symbol[] {
					sem_StartRelationExpression, arithExpr, exprPrime
			}
		});
		p.put(exprPrime, new Symbol[][] {
			new Symbol[] {
				relOp, sem_PushRelationOperation, sem_StartRelationExpression, arithExpr, exprPrime
			},
			new Symbol[] {
				EPSILON, sem_EndRelationExpression
			},
		});
		p.put(relExpr, new Symbol[][] {
			new Symbol[] {
				sem_StartRelationExpression, arithExpr, relOp, sem_PushRelationOperation, arithExpr, sem_EndRelationExpression,
			}
		});
		p.put(arithExpr, new Symbol[][] {
			new Symbol[] {
				sem_StartAdditionExpression, term, arithExprPrime,
			}
		});
		p.put(arithExprPrime, new Symbol[][] {
			new Symbol[] {
				addOp, sem_PushAdditionOperation, arithExpr,
			}, new Symbol[] {
				EPSILON, sem_EndAdditionExpression
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
				sem_StartMultiplicationExpression, factor, termPrime,
			}
		});
		p.put(termPrime, new Symbol[][] {
			new Symbol[] {
				multOp, sem_PushMultiplicationOperation, sem_StartMultiplicationExpression, factor, termPrime,
			}, new Symbol[] {
				EPSILON, sem_EndMultiplicationExpression
			}
		});
		p.put(factor, new Symbol[][] {
			new Symbol[] {
				factorIdNest,
			}, new Symbol[] {
				tok_int_literal, sem_PushIntLiteral
			}, new Symbol[] {
				tok_float_literal, sem_PushFloatLiteral
			}, new Symbol[] {
				tok_open_paren, arithExpr, tok_close_paren // TODO
			}, new Symbol[] {
				tok_not, factor, // TODO
			}, new Symbol[] {
				sign, factor  // TODO
			}
		});
		p.put(factorIdNest, new Symbol[][] {
			new Symbol[] {
				tok_id, factorIdNestPrime,
			}
		});
		p.put(factorIdNestPrime, new Symbol[][] {
			new Symbol[] {
				sem_PushVariableName, indices, factorIdNestPrimePrime,
			}, new Symbol[] {
				sem_StartFunctionCall, tok_open_paren, aParams, tok_close_paren, sem_EndFunctionCall,
			}
		});
		p.put(factorIdNestPrimePrime, new Symbol[][] {
			new Symbol[] {
				tok_dot, factorIdNest,
			}, new Symbol[] {
				EPSILON, sem_FinishVariable
			}
		});
		p.put(variable, new Symbol[][] {
			new Symbol[] {
				tok_id, sem_PushVariableName, indices, variablePrime
			}
		});
		p.put(variablePrime, new Symbol[][] {
			new Symbol[] {
				tok_dot, variable
			}, new Symbol[] {
				EPSILON, sem_FinishVariable
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
					// TODO - need to push entire arithExpr, not just assume int literal
				tok_open_square, arithExpr, tok_close_square
			}
		});
		p.put(arraySize, new Symbol[][] {
			new Symbol[] {
				tok_open_square, tok_int_literal, sym_StoreDimension, tok_close_square
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
				type, sym_StoreType, tok_id, sym_StoreId, fParamsArraySz,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(fParamsArraySz, new Symbol[][] {
			new Symbol[] {
				arraySize, fParamsArraySz,
			}, new Symbol[] {
				sym_AddFunctionParameter, fParamsTailStar,
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
				tok_comma, type, sym_StoreType, tok_id, sym_StoreId, arraySizeStar, sym_AddFunctionParameter
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
	
	private static String toHtmlString(Symbol[] rule){
		StringBuilder s = new StringBuilder();
		s.append(rule[0].toHtmlString());
		for(int i = 1; i < rule.length; ++i){
			s.append(' ');
			s.append(rule[i].toHtmlString());
		}
		return s.toString();
	}
	
	public static void exportHtml(File f) throws FileNotFoundException{
		PrintWriter out = new PrintWriter(f);
		
		out.write("<html><head>"
				+ "<style>"
				+ "body {"
				+ "	font-family:monospace;"
				+ "}"
				+ "</style>"
				+ "</head><body><table>");
		for(Symbol lhs : Symbol.values()){
			if (! lhs.isNonterminal()) continue;
			
			out.write("<tr>");
			Symbol [][] rhss = Grammar.productions.get(lhs);
			out.print("<td>" + lhs.toHtmlString() + "</td>");
			out.print("<td>::=</td>");
			out.print("<td>" + toHtmlString(rhss[0]) + "</td>");
			out.write("</tr>");
			for(int i = 1 ; i < rhss.length; ++i){				
				out.print("<tr><td></td><td>|</td><td>");
				out.print(toHtmlString(rhss[i]));
				out.write("</td></tr>");
			}
		}
		out.write("</table></body></html>");
		
		out.flush();
		out.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		exportHtml(new File("grammar.html"));
	}
}
