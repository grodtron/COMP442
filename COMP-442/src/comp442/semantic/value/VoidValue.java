package comp442.semantic.value;


public class VoidValue implements Value {

	private VoidValue(){}
	
	private static VoidValue instance = new VoidValue();
	
	public static VoidValue get(){
		return instance;
	}
}
