import java.io.PrintStream;
import java.util.NoSuchElementException;

public class GenericQueueWithOnePointer<T> {
	
	private GenericNode<T> lastOfQueue;
    private int sizeOfQueue;
    private T dataRemoved;
    //checking if empty
	public boolean isEmpty(){
		return(lastOfQueue == null);
	}

	//putting item/node in queue
	public void put(T Item){
			
		
		if(isEmpty()){
			lastOfQueue = new GenericNode<T>(Item);
			lastOfQueue.next = lastOfQueue;
		}else{
			GenericNode<T> newNode = new GenericNode<T>(Item);
			newNode.next = lastOfQueue.next;
			lastOfQueue.next = newNode;
			lastOfQueue = newNode;
		}
		
		sizeOfQueue += 1;
		
	}

	//removing item/node from queue
	public T get() throws NoSuchElementException{
		if(isEmpty()){
			throw new NoSuchElementException();
		}else{
			if(size() == 2){
				dataRemoved = lastOfQueue.next.Item;
				lastOfQueue.next = lastOfQueue;			
				sizeOfQueue -= 1;
				return (T) dataRemoved;
			}else if(size() == 1){
				dataRemoved = lastOfQueue.Item;
				lastOfQueue.next = null;
				lastOfQueue = null;
				sizeOfQueue -= 1;
				return (T) dataRemoved;
				
			}else{
				dataRemoved = lastOfQueue.next.Item;
				lastOfQueue.next = lastOfQueue.next.next;			
				sizeOfQueue -= 1;
				return (T) dataRemoved;
			}
			
		}
	}

    //returning without removing item from queue
	public T peek() throws NoSuchElementException{
		if(isEmpty()){
			throw new NoSuchElementException();
		}else{
			return lastOfQueue.next.Item;
		}
	}


	//printing entire queue
	public void printQueue(PrintStream stream){
		if(isEmpty()){
			throw new NoSuchElementException();
		}else{
			GenericNode<T> cur = lastOfQueue.next;
			while(cur.next != lastOfQueue.next){
				System.out.println(cur.Item);
				cur = cur.next;
			}
			System.out.println(cur.Item);
		}
	}

	//getting size of queue
	public int size(){
		if(isEmpty()){
			return 0;
		}else{
			return sizeOfQueue;
		}
	}
	
	public String returnItems(){
		String items = "";
		if(isEmpty()){
			throw new NoSuchElementException();
		}else{
			GenericNode<T> cur = lastOfQueue.next;
			while(cur.next != lastOfQueue.next){
				items += cur.Item + "-";
				cur = cur.next;
			}
			items += cur.Item;
		}
		return items;
	}
	
}
