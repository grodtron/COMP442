package comp442.semantic.expressions;

import java.util.Collections;
import java.util.List;

import comp442.error.CompilerError;
import comp442.error.InternalCompilerError;
import comp442.semantic.symboltable.entries.types.ArrayType;

public class IndexingExpressionFragment extends ExpressionElement {
	
	private int offset;
	private int currentIndex;
	private int nextOffsetSize;
	private List<Integer> dimensions;
	
	
	public IndexingExpressionFragment(ArrayType t){
		dimensions = t.getDimensions();
		currentIndex = 0;
		nextOffsetSize = t.getType().getSize();
		offset = 0;
	}
	
	public IndexingExpressionFragment(){
		dimensions = Collections.emptyList();
		currentIndex = 0;
		nextOffsetSize = 0;
		offset = 0;
	}
	
	@Override
	public void pushIdentifier(String id) throws CompilerError {
		throw new CompilerError("Not enough indices, got " + currentIndex + ", expected " + dimensions.size());
	}
	
	@Override
	public void pushIndex(String iString) throws CompilerError{
		int i;
		try{
			i = Integer.valueOf(iString);
			i -= 1; // we will internally use zero-based indexing
		}catch(NumberFormatException e){
			throw new InternalCompilerError("Invalid index: " + e.getMessage());
		}
		
		if(i >= dimensions.get(currentIndex)){
			throw new CompilerError("Index " + i + " out of bounds");
		}else{
			offset += i * nextOffsetSize;
			nextOffsetSize *= dimensions.get(currentIndex);
			++currentIndex;
			if(currentIndex == dimensions.size()){
				context.finishTopElement();
			}
		}
	}
	
	public int getOffset() throws CompilerError{
		if(currentIndex != dimensions.size()){
			throw new CompilerError("Not enough indices");
		}else{
			return offset;			
		}
	}
}
