package comp442.error;

public class CompilerError extends Exception {

	private static final long serialVersionUID = 1L;

	public CompilerError(String message){
		super("Error: " + message);
	}
	
}
