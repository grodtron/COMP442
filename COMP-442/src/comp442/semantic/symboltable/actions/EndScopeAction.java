package comp442.semantic.symboltable.actions;

import comp442.lexical.token.Token;
import comp442.semantic.symboltable.SymbolAction;
import comp442.semantic.symboltable.SymbolTable;

public class EndScopeAction extends SymbolAction {
	
	@Override
	public void execute(Token token) {
		if(context.skipNextCloseScope){
			context.skipNextCloseScope = false;
		}else{
			SymbolTable parent = context.currentSymbolTable.getParent();
			if(parent != null){
				context.currentSymbolTable = parent;
			}else{
				throw new RuntimeException("Tried to close global scope");
			}
		}
	}

}
