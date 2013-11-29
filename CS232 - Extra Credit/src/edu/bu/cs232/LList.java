package edu.bu.cs232;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LList<E> implements List<E> {
	protected class LListItem<T> {
		private LListItem<T> next;
		private LListItem<T> previous;
		
		private final T data;
		
		public LListItem(T data) {
			this(data, null, null);
		}
		public LListItem(T data, LListItem<T> previous, LListItem<T> next) {
			this.data = data;
			this.setNext(next);
			this.setPrevious(previous);
		}
		
		public T get() {
			return this.data;
		}
		
		
		public LListItem<T> setNext(LListItem<T> next) {
			LListItem<T> result = this.next;
			this.next = next;
			return result;
		}
		public LListItem<T> setPrevious(LListItem<T> previous) {
			LListItem<T> result = this.previous;
			this.previous = previous;
			return result;
		}
	}

	protected class LListIterator<T> implements Iterator<T> {

		protected LListItem<T> current;
		protected LListItem<T> next;

		LListIterator(LListItem<T> first) {
			this.current = first; 
			this.next = first;
		}
		@Override
		public boolean hasNext() {
			return (this.next != null);
		}

		@Override
		public T next() {
			if (!this.hasNext()) {
				throw new ArrayIndexOutOfBoundsException();
			}
			LListItem<T> result = this.next;
			this.current = result;
			this.next = result.next;
			return result.data;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove not available.");
		}
		
	}

	protected class LListListIterator<T> extends LListIterator<T> implements ListIterator<T> {
		protected LList<T> theList;
		protected LListItem<T> previous;
		
		LListListIterator(LList<T> theList, LListItem<T> first) {
			super(first);
			this.theList = theList;
			this.previous = first.previous;
		}
		@SuppressWarnings("unchecked")
		LListListIterator(LList<T> theList) {
			this(theList, (LListItem<T>) theList.getListItem(0));
		}

		@Override
		public T next() {
			this.previous = this.current;
			return super.next();
		}
		
		@Override
		public boolean hasPrevious() {
			return (this.previous != null);
		}

		@Override
		public T previous() {
			if (!this.hasPrevious()) {
				throw new ArrayIndexOutOfBoundsException();
			}
			LListItem<T> result = this.previous;
			this.current = result;
			this.next = result.next;
			this.previous = result.previous;
			return result.data;
		}

		@Override
		public int nextIndex() {
			return -1;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			return -1;
		}
		@Override
		public void set(T e) {
			throw new UnsupportedOperationException("Set not available.");
		}

		@Override
		public void add(T e) {
			throw new UnsupportedOperationException("Add not available.");
		}
		
	}

	private int elementCount;
	private LListItem<E> first;
	private LListItem<E> last;
	
	public LList() {
		this(null);
	}
	
	public LList(LListItem<E> first) {
		this.first = first;
		this.last = first;
		if (this.last != null) {
			while(this.last.next != null) {
				this.last = this.last.next;
			}
		}
	}
	
	@Override
	public int size() {
		return this.elementCount;
	}

	@Override
	public boolean isEmpty() {
		return (this.elementCount == 0);
	}

	@Override
	public boolean contains(Object o) {
		for (E element : this) {
			if (element.equals(o)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return new LListIterator<E>(this.first);
	}

	@Override
	public Object[] toArray() {
		return this.toArray(new Object[this.elementCount]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < this.elementCount) {
			a = (T[]) Array.newInstance(a.getClass(), this.elementCount);
			System.out.println(a.getClass().toString());
		}
		int i = 0;
		for (E element : this) {
			a[i++] = (T) element;
		}
		if (a.length > this.elementCount) {
			a[this.elementCount] = null;
		}
		return a;
	}

	@Override
	public boolean add(E e) {
		return this.push(e);
	}

	public boolean push(E e) {
		LListItem<E> item = new LListItem<>(e, this.last, null);
		if (this.last == null) {
			this.first = item;
		} else {
			this.last.setNext(item);
		}
		this.last = item;
		++this.elementCount;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		E data = this.remove(this.indexOf(o));
		return (data != null); 
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (E element : this) {
			if (!c.contains((Object) element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return this.addAll(this.elementCount - 1, c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		for (E element : c) {
			try {
				this.add(element);
			} catch (Exception ex) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object o : c) {
			try {
				this.remove(o);
			} catch (Exception ex) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		for (Object o : this) {
			if (!c.contains(o)) {
				this.remove(o);
			}
		}
		return true;
	}

	@Override
	public void clear() {
		this.elementCount = 0;
		this.first = null;
	}

	protected LListItem<E> getListItem(int index) {
		LListItem<E> current = this.first;
		for (int i = 1; i <= index; ++i) {
			current = current.next;
			if (current == null) {
				throw new ArrayIndexOutOfBoundsException();
			}
		}
		return current;
	}
	
	@Override
	public E get(int index) {
		return this.getListItem(index).data;
	}

	@Override
	public E set(int index, E element) {
		return null;
	}

	@Override
	public void add(int index, E element) {
		if (index > this.elementCount) {
			throw new ArrayIndexOutOfBoundsException();
		}
		LListItem<E> follower = this.getListItem(index);
		LListItem<E> insert = new LListItem<>(element, follower.previous, follower);
		if (index == 0) {
			this.first = insert;
		}
	}

	@Override
	public E remove(int index) {
		LListItem<E> item = this.getListItem(index);
		if (item == null) {
			return null;
		}
		if (item.next != null) {
			item.next.setPrevious(item.previous);
		}
		if (item.previous != null) {
			item.previous.setNext(item.next);
		}
		if (this.first == item) {
			this.first = item.next;
		}
		if (this.last == item) {
			this.last = item.previous;
		}
		return item.data;
	}

	@Override
	public int indexOf(Object o) {
		int result = -1;
		for (E element : this) {
			++result;
			if (element.equals(o)) {
				break;
			}
		}
		return result;
	}

	@Override
	public int lastIndexOf(Object o) {
		int result = -1;
		LListItem<E> current = this.last;
		for (int i = this.elementCount - 1; current != null; --i) {
			result = i;
			if (current.data.equals(o)) {
				break;
			}
			current = current.previous;
		}
		return result;
	}

	@Override
	public ListIterator<E> listIterator() {
		return this.listIterator(0);
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new LListListIterator<E>(this, this.getListItem(index));
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		LList<E> result = new LList<E>();
		for (int i = fromIndex; i <= toIndex; ++i) {
			result.add(this.get(i));
		}
		return result;
	}
}
