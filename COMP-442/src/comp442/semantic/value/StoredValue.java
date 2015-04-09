package comp442.semantic.value;

public class StoredValue implements Value {

	private final Value offset;
	private final Value baseAddress;

	public StoredValue(Value baseAddress, Value offset){
		this.baseAddress = baseAddress;
		this.offset      = offset;
	}

	public Value getOffset() {
		return offset;
	}

	public Value getBaseAddress() {
		return baseAddress;
	}
	
	@Override
	public String toString() {
		return "*(" + baseAddress + " + " + offset + ")";
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof StoredValue 
				&& ((StoredValue)other).getBaseAddress().equals(baseAddress) 
				&& ((StoredValue)other).getOffset().equals(offset); 
	}
	
}
