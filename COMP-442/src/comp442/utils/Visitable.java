package comp442.utils;

public interface Visitable<T> {

	public void acceptVisitor(Visitor<T> visitor);
	
	public T get();
	
}
