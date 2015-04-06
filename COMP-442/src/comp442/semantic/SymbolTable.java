package comp442.semantic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import comp442.semantic.symboltable.entries.SymbolTableEntry;

public class SymbolTable {

	// TODO is it necessary to have this as a LinkedHashMap ?
	private Map<String, SymbolTableEntry> entries;
		
	int currentOffset;
	
	private final SymbolTable parent;
	
	public SymbolTable(SymbolTable parent){
		this.parent = parent;
		this.entries = new HashMap<String, SymbolTableEntry>();
		this.currentOffset = 0;
	}

	public boolean exists(String name) {
		return entries.get(name) != null;
	}
	
	public SymbolTableEntry find(String name){
		SymbolTableEntry e = entries.get(name);
		if(e != null){
			return e;
		}else{
			if(parent != null){
				return parent.find(name);
			}else{
				return null;
			}
		}
	}

	public void add(SymbolTableEntry entry) {
		// Note that for functions, parameters are added in order
		entries.put(entry.getName(), entry);
		
		entry.setOffset(currentOffset);
		currentOffset += entry.getSize();
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
				sb.append("   ");
			}
		}
	}

	public Collection<SymbolTableEntry> getEntries() {
		return Collections.unmodifiableCollection(entries.values());
	}

}
