package comp442.semantic.expressions;

import java.util.Collections;
import java.util.List;

import comp442.error.CompilerError;
import comp442.semantic.symboltable.entries.types.ArrayType;
import comp442.semantic.symboltable.entries.types.SymbolTableEntryType;

public class ArrayIndexBuilder extends ExpressionElement {
	
	private int offset;
	private int currentIndex;
	private int nextOffsetSize;
	private List<Integer> dimensions;
	
	
	public ArrayIndexBuilder(ArrayType t){
		dimensions = t.getDimensions();
		currentIndex = 0;
		nextOffsetSize = t.getType().getSize();
		offset = 0;
	}
	
	public ArrayIndexBuilder(){
		dimensions = Collections.emptyList();
		currentIndex = 0;
		nextOffsetSize = 0;
		offset = 0;
	}
	
	public void pushIndex(int i) throws CompilerError{
		if(currentIndex == dimensions.size()){
			throw new CompilerError("Too many indices");
		}else
		if(i >= dimensions.get(currentIndex)){
			throw new CompilerError("Index " + i + " out of bounds");
		}else{
			offset += i * nextOffsetSize;
			nextOffsetSize *= dimensions.get(currentIndex);
			++currentIndex;
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
