package comp442.syntactical.data;
import static comp442.syntactical.data.Symbol.EPSILON;
import static comp442.syntactical.data.Symbol.aParams;
import static comp442.syntactical.data.Symbol.aParamsTail;
import static comp442.syntactical.data.Symbol.aParamsTailStar;
import static comp442.syntactical.data.Symbol.addOp;
import static comp442.syntactical.data.Symbol.arithExpr;
import static comp442.syntactical.data.Symbol.arithExprPrime;
import static comp442.syntactical.data.Symbol.arraySize;
import static comp442.syntactical.data.Symbol.arraySizeStar;
import static comp442.syntactical.data.Symbol.assignExpr;
import static comp442.syntactical.data.Symbol.assignOp;
import static comp442.syntactical.data.Symbol.assignStat;
import static comp442.syntactical.data.Symbol.classBody;
import static comp442.syntactical.data.Symbol.classBodyFunc;
import static comp442.syntactical.data.Symbol.classBodyFuncPrime;
import static comp442.syntactical.data.Symbol.classBodyPrime;
import static comp442.syntactical.data.Symbol.classBodyVar;
import static comp442.syntactical.data.Symbol.classDecl;
import static comp442.syntactical.data.Symbol.controlStat;
import static comp442.syntactical.data.Symbol.expr;
import static comp442.syntactical.data.Symbol.exprPrime;
import static comp442.syntactical.data.Symbol.fParams;
import static comp442.syntactical.data.Symbol.fParamsArraySz;
import static comp442.syntactical.data.Symbol.fParamsTail;
import static comp442.syntactical.data.Symbol.fParamsTailStar;
import static comp442.syntactical.data.Symbol.factor;
import static comp442.syntactical.data.Symbol.factorIdNest;
import static comp442.syntactical.data.Symbol.factorIdNestPrime;
import static comp442.syntactical.data.Symbol.factorIdNestPrimePrime;
import static comp442.syntactical.data.Symbol.funcBody;
import static comp442.syntactical.data.Symbol.funcBodyStmt;
import static comp442.syntactical.data.Symbol.funcBodyVar;
import static comp442.syntactical.data.Symbol.funcBodyVarPrime;
import static comp442.syntactical.data.Symbol.funcDefs;
import static comp442.syntactical.data.Symbol.funcDefsPrime;
import static comp442.syntactical.data.Symbol.indice;
import static comp442.syntactical.data.Symbol.indices;
import static comp442.syntactical.data.Symbol.multOp;
import static comp442.syntactical.data.Symbol.prog;
import static comp442.syntactical.data.Symbol.progBody;
import static comp442.syntactical.data.Symbol.relExpr;
import static comp442.syntactical.data.Symbol.relOp;
import static comp442.syntactical.data.Symbol.sign;
import static comp442.syntactical.data.Symbol.statBlock;
import static comp442.syntactical.data.Symbol.statBlockStmts;
import static comp442.syntactical.data.Symbol.term;
import static comp442.syntactical.data.Symbol.termPrime;
import static comp442.syntactical.data.Symbol.tok_and;
import static comp442.syntactical.data.Symbol.tok_assignment;
import static comp442.syntactical.data.Symbol.tok_class;
import static comp442.syntactical.data.Symbol.tok_close_brace;
import static comp442.syntactical.data.Symbol.tok_close_paren;
import static comp442.syntactical.data.Symbol.tok_close_square;
import static comp442.syntactical.data.Symbol.tok_comma;
import static comp442.syntactical.data.Symbol.tok_diamond;
import static comp442.syntactical.data.Symbol.tok_dot;
import static comp442.syntactical.data.Symbol.tok_else;
import static comp442.syntactical.data.Symbol.tok_equals;
import static comp442.syntactical.data.Symbol.tok_float;
import static comp442.syntactical.data.Symbol.tok_float_literal;
import static comp442.syntactical.data.Symbol.tok_for;
import static comp442.syntactical.data.Symbol.tok_get;
import static comp442.syntactical.data.Symbol.tok_greater_than;
import static comp442.syntactical.data.Symbol.tok_greater_than_equals;
import static comp442.syntactical.data.Symbol.tok_id;
import static comp442.syntactical.data.Symbol.tok_if;
import static comp442.syntactical.data.Symbol.tok_int;
import static comp442.syntactical.data.Symbol.tok_int_literal;
import static comp442.syntactical.data.Symbol.tok_less_than;
import static comp442.syntactical.data.Symbol.tok_less_than_equals;
import static comp442.syntactical.data.Symbol.tok_minus;
import static comp442.syntactical.data.Symbol.tok_not;
import static comp442.syntactical.data.Symbol.tok_open_brace;
import static comp442.syntactical.data.Symbol.tok_open_paren;
import static comp442.syntactical.data.Symbol.tok_open_square;
import static comp442.syntactical.data.Symbol.tok_or;
import static comp442.syntactical.data.Symbol.tok_plus;
import static comp442.syntactical.data.Symbol.tok_program;
import static comp442.syntactical.data.Symbol.tok_put;
import static comp442.syntactical.data.Symbol.tok_return;
import static comp442.syntactical.data.Symbol.tok_semicolon;
import static comp442.syntactical.data.Symbol.tok_slash;
import static comp442.syntactical.data.Symbol.tok_star;
import static comp442.syntactical.data.Symbol.tok_then;
import static comp442.syntactical.data.Symbol.type;
import static comp442.syntactical.data.Symbol.varDeclArray;
import static comp442.syntactical.data.Symbol.variable;
import static comp442.syntactical.data.Symbol.variablePrime;

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
				tok_class, tok_id, tok_open_brace, classBody,
			}
		});
		p.put(classBody, new Symbol[][] {
			new Symbol[] {
				type, tok_id, classBodyPrime,
			},
			new Symbol[] {
				tok_close_brace, tok_semicolon,
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
				varDeclArray, classBody,
			}, new Symbol[] {
				classBody,
			}
		});
		p.put(classBodyFunc, new Symbol[][] {
			new Symbol[] {
				tok_open_paren, fParams, tok_close_paren, funcBody, tok_semicolon, classBodyFuncPrime,
			}
		});
		p.put(classBodyFuncPrime, new Symbol[][] {
			new Symbol[] {
				type, tok_id, classBodyFunc,
			}, new Symbol[] {
				tok_close_brace, tok_semicolon,
			}
		});
		
		p.put(progBody, new Symbol[][] {
			new Symbol[] {
				tok_program, funcBody, tok_semicolon, funcDefsPrime,
			}
		});
		p.put(funcDefs, new Symbol[][] {
			new Symbol[] {
				tok_open_paren, fParams, tok_close_paren, funcBody, tok_semicolon, funcDefsPrime,
			}, new Symbol[] {
				funcDefsPrime
			}
		});
		p.put(funcDefsPrime, new Symbol[][] {
			new Symbol[] {
				type, tok_id, funcDefs,
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
				tok_int, tok_id, varDeclArray, funcBodyVar,
			}, new Symbol[] {
				tok_float, tok_id, varDeclArray, funcBodyVar,
			}, new Symbol[] {
				tok_id, funcBodyVarPrime
			}, new Symbol[] {
				controlStat, funcBodyStmt,
			}, new Symbol[] {
				tok_close_brace
			}
		});
		p.put(funcBodyVarPrime, new Symbol[][] {
			new Symbol[] {
				tok_id, varDeclArray, funcBodyVar,
			}, new Symbol[] {
				indices, variablePrime, assignStat, funcBodyStmt,
			}
		});
		p.put(funcBodyStmt, new Symbol[][] {
			new Symbol[] {
				variable, assignStat, funcBodyStmt,
			}, new Symbol[] {
				controlStat, funcBodyStmt,
			}, new Symbol[] {
				tok_close_brace
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
				tok_if, tok_open_paren, expr, tok_close_paren, tok_then, statBlock, tok_else, statBlock, tok_semicolon
			}, new Symbol[] {
				tok_for, tok_open_paren, type, tok_id, assignOp, expr, tok_semicolon, relExpr, tok_semicolon, variable, assignExpr, tok_close_paren, statBlock, tok_semicolon
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
				tok_open_brace, statBlockStmts,
			}, new Symbol[] {
				variable, assignStat,
			}, new Symbol[] {
				controlStat,
			}, new Symbol[] {
				EPSILON
			}
		});
		p.put(statBlockStmts, new Symbol[][] {
			new Symbol[] {
				variable, assignStat, statBlockStmts,
			},
			new Symbol[] {
				controlStat, statBlockStmts,
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
			if (lhs.isTerminal) continue;
			
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
