package comp442.semantic.value;

public class StaticIntValue extends StaticValue implements Value {

	private final int v;
	
	public StaticIntValue(int v){
		this.v = v;
	}
	
	@Override
	public int intValue() {
		return v;
	}

	@Override
	public float floatValue() {
		return (float) v;
	}

	@Override
	public String toString() {
		return Integer.toString(v);
	}
	
	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return other instanceof StaticIntValue && ((StaticIntValue)other).intValue() == v;
	}
	
}
