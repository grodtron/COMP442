package comp442.semantic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import comp442.semantic.symboltable.entries.SymbolTableEntry;

public class SymbolTable {

	private Map<String, SymbolTableEntry> entries;
	private final SymbolTable parent;
	
	public SymbolTable(SymbolTable parent){
		this.parent = parent;
		this.entries = new HashMap<String, SymbolTableEntry>();
	}

	public SymbolTableEntry find(String name) {
		return entries.get(name);
	}

	public void add(SymbolTableEntry entry) {
		entries.put(entry.getName(), entry);
	}

	public SymbolTable getParent() {
		return parent;
	}
	
	@Override
	public String toString(){
		return new SymbolTableStringifier().toString(this);
	}
	
	private class SymbolTableStringifier {

		private int indent = 0;
		private StringBuilder sb = new StringBuilder();
		
		public String toString(SymbolTable s){
			for(Entry<String, SymbolTableEntry> e : s.entries.entrySet()){
				SymbolTableEntry ste = e.getValue();
				sb.append(ste);
				if(ste.getScope() != null){
					++indent;
					newline();
					toString(ste.getScope());
					--indent;
				}
				newline();
			}
			return sb.toString();
		}
		
		private void newline(){
			sb.append('\n');
			for(int i = 0; i < indent; ++i){
				sb.append(' ');
			}
		}
	}

}
