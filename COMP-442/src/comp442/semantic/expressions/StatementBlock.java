package comp442.semantic.expressions;

import java.util.ArrayList;
import java.util.List;

import comp442.codegen.CodeGenerationContext;
import comp442.error.CompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.value.Value;
import comp442.semantic.value.VoidValue;

public class StatementBlock extends ExpressionElement implements Statement {

	private List<Statement> statements;
	
	public StatementBlock() {
		statements = new ArrayList<Statement>();
	}
	
	@Override
	public void acceptSubElement(ExpressionElement e) throws CompilerError {
		if(e instanceof Statement){
			statements.add((Statement) e);
		}else{
			super.acceptSubElement(e);
		}
	}
	
	@Override
	public void generateCode(CodeGenerationContext c) throws CompilerError {
		for(Statement s : statements){
			c.commentNext(s.getClass().getSimpleName());
			s.generateCode(c);
		}
	}

	@Override
	public String pseudoCode() {
		StringBuilder sb = new StringBuilder();
		for(Statement s : statements){
			sb.append(s.pseudoCode());
		}
		return sb.toString();
	}

	@Override
	public Value getValue() throws CompilerError {
		return VoidValue.get(); // block doesn't have a value
	}

}
