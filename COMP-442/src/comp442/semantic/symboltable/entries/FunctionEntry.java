package comp442.semantic.symboltable.entries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp442.error.CompilerError;
import comp442.semantic.statement.Statement;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.types.FunctionType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class FunctionEntry extends SymbolTableEntry {
	
	private List<Statement> statements; 
	
	private final String label;
	
	public FunctionEntry(String name, SymbolTableEntryType returnType, SymbolTable table) {
		super(name, Kind.Function, new FunctionType(returnType, new ArrayList<SymbolTableEntryType>()), table);
		statements = new ArrayList<Statement>();
		this.label = name + Integer.toString(hashCode());
	}

	public void addParameter(ParameterEntry param) throws CompilerError {
		FunctionType type = (FunctionType)getType();
		type.pushParameter(param.getType());
		
		getScope().add(param);
	}

	@Override
	protected int calculateSize() throws CompilerError {
		int size = 0;
		for(SymbolTableEntry e : getScope().getEntries()){
			if(e instanceof ParameterEntry || e instanceof VariableEntry){
				size += e.getSize();
			}
		}
		return size;
	}
	
	public void appendStatement(Statement s){
		statements.add(s);
	}

	public List<Statement> getStatements() {
		return Collections.unmodifiableList(statements);
	}

	public String getLabel() {
		return label;
	}
}
