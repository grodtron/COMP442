package comp442.codegen.instructions;

public abstract class Instruction {

	private String label;
	private String comment;
	
	public Instruction(){
		label = "";
		comment = "";
	}
	
	// need a better name for this
	protected abstract String _getCode();
	
	public String getCode(){
		return label + "\t" + _getCode() + "\t% " + comment;
	}

	public Instruction setLabel(String label) {
		this.label = label;
		return this;
	}

	public Instruction setComment(String comment) {
		this.comment = comment;
		return this;
	}
	
}
