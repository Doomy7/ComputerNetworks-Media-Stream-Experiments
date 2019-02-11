
public class GenericNode<T> {

	T Item;
	GenericNode<T> next;
	
	GenericNode(T Item, GenericNode<T> next){
		this.Item = Item;
		this.next = next;
		next = null;
	}
	
	GenericNode(T Item){
		this.Item = Item;
	}
	
	T getItem(){
		return Item;
	}
	

	
}
