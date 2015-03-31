package comp442.semantic.action;

import comp442.lexical.token.Token;
import comp442.semantic.SymbolTable;

public class EndScopeAction implements SemanticAction {

	private final static SemanticContext context = SemanticContext.instance;
	
	@Override
	public void execute(Token token) {
		if(context.skipNextCloseScope){
			context.skipNextCloseScope = false;
		}else{
			SymbolTable parent = context.currentSymbolTable.getParent();
			if(parent != null){
				context.currentSymbolTable = parent;
				System.out.println("Ended current scope");
			}else{
				throw new RuntimeException("Tried to close global scope");
			}
		}
	}

}
