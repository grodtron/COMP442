package comp442.utils;

import java.util.List;

public class VisitorAcceptor<T> {

	Visitable<T> visitable;
	List<? extends Visitable<T>> children;
	
	public VisitorAcceptor(	Visitable<T> visitable, List<? extends Visitable<T>> children ){
		this.visitable = (Visitable<T>) visitable;
		this.children = children;
	}
	
	public void acceptVisitor(Visitor<T> v){
		v.visit(visitable);
		v.push();
		for(Visitable<T>  child : children){
			child.acceptVisitor(v);
		}
		v.pop();
	}
	
}
