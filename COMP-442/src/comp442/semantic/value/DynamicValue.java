package comp442.semantic.value;


public abstract class DynamicValue implements Value {

	@Override
	public boolean isStatic() {
		return false;
	}

}
