package comp442.utils;

public interface Visitor<T> {

	public void visit(Visitable<T> host);
	public void push();
	public void pop();
	
}
