package structures;

public class DoublyCircularLinkedListImplementation<T extends Comparable<T>> implements
		DoublyCicularLinkedList<T> {
	private int listSize;
	private DLLNode<T> list;
	private DLLNode<T> location;
	private DLLNode<T> previous;
	private DLLNode<T> currentPos;
	private boolean found;

	public DoublyCircularLinkedListImplementation() {
		listSize = 0;
		list = null;
		location = null;
		previous = null;
		currentPos = null;
		found = false;
	}

	@Override
	public int size() {
            return listSize;
	}

	@Override
	public void add(T element) {
		DLLNode<T> newNode = new DLLNode<T>(element);
		if (list == null){
			list = newNode;
			newNode.setForward(list);
			newNode.setBack(list);
		}
		else{
			newNode.setForward(list);
			newNode.setBack(list.getBack());
			list.getBack().setForward(newNode);
			list.setBack(newNode);
		}
		listSize++;
	}

	@Override
	public boolean remove(T element) {
		find(element);
		if(found){
			if(list == list.getInfo()){
				list = null;
			}
			else{
				if(previous.getInfo() == list){
					list = previous;
				}
				previous.setForward(location.getForward());
				listSize--;
			}
		}
		return found;
	}


	@Override
	public boolean contains(T element) {
			find(element);
            return found;
	}

	@Override
	public T get(T element) {
		find(element);
		if(found){
			return location.getInfo();
		}
		else{
			return null;
		}
	}

	@Override
	public void reset() {
		currentPos = list;
	}

	@Override
	public T getNext() {
		if(currentPos == null){
			return null;
		}
		else{
			T nextElem = currentPos.getInfo();
			currentPos = currentPos.getForward();
			return nextElem;
		}

	}

	@Override
	public T getPrevious() {
		if(currentPos == null){
			return null;
		}
		else{
			T prevElem = currentPos.getBack().getInfo();
			currentPos = currentPos.getBack();
			return prevElem;
		}
	}

	private T find(T target) {
		location = list;
		found = false;
		if (list != null) {
			do {
				previous = location;
				location = location.getForward();

				if (location.getInfo().equals(target)) {
					found = true;
				}
			} while ((location != list) && !found);
		}
		return target;
	}

}
