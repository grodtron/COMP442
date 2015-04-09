package comp442.semantic.symboltable.entries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp442.semantic.statement.Statement;
import comp442.semantic.symboltable.SymbolTable;
import comp442.semantic.symboltable.entries.types.FunctionType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class FunctionEntry extends SymbolTableEntry {
	
	private List<Statement> statements; 
	
	public FunctionEntry(String name, SymbolTableEntryType returnType, SymbolTable table) {
		super(name, Kind.Function, new FunctionType(returnType, new ArrayList<SymbolTableEntryType>()), table);
		statements = new ArrayList<Statement>();
	}

	public void addParameter(ParameterEntry param) {
		FunctionType type = (FunctionType)getType();
		type.pushParameter(param.getType());
		
		getScope().add(param);
	}

	@Override
	protected int calculateSize() {
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
}
