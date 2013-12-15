package edu.bu.cs232;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LList<E> implements List<E> {
	protected class LListItem<T> {
		private final T data;
		private LListItem<T> next;
		
		private LListItem<T> previous;
		
		public LListItem(T data) {
			this(data, null, null);
		}
		public LListItem(T data, LListItem<T> previous, LListItem<T> next) {
			this.data = data;
			this.setNext(next);
			this.setPrevious(previous);
		}

		@Override
		public boolean equals(Object o) {
			if (null == o) {
				return false;
			}
			if (this == o) {
				return true;
			}
			try {
				@SuppressWarnings("unchecked")
				LListItem<T> node = (LListItem<T>)o;
				return this.get().equals(node.get());
			} catch (ClassCastException ex) {
				return false;
			}
		}
		
		public T get() {
			return this.data;
		}
		protected String getStringRepresentation(LListItem<T> node) {
			if (null == node) {
				return "null";
			} else {
				T data = node.get();
				if (null == data) {
					return "<null>";
				} else {
					return data.toString();
				}
			}
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
		@Override
		public String toString() {
			return String.format("%s <- %s -> %s", this.getStringRepresentation(this.previous),
											       this.getStringRepresentation(this),
											       this.getStringRepresentation(this.next));
		}
	}

	protected class LListIterator<T> implements Iterator<T> {

		protected LListItem<T> current;
		protected LListItem<T> next;

		LListIterator(LListItem<T> first) {
			this.current = null; 
			this.next = first;
		}
		@Override
		public boolean hasNext() {
			return (this.next != null);
		}

		@Override
		public T next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			LListItem<T> result = this.next;
			this.current = result;
			this.next = result.next;
			return result.data;
		}

		@Override
		public void remove() {
			if (null == this.current) {
				throw new IllegalStateException("Element at current position does not exist.");
			}
			if (null != this.current.previous) {
				this.current.previous.next = this.current.previous;
			}
			if (null != this.current.next) {
				this.current.next.previous = this.current.previous;
			}
			this.current = null;
		}
		
	}

	protected class LListListIterator<T> extends LListIterator<T> implements ListIterator<T> {
		protected LListItem<T> previous;
		protected LList<T> theList;
		
		@SuppressWarnings("unchecked")
		LListListIterator(LList<T> theList) {
			this(theList, (LListItem<T>) theList.getListItem(0));
		}
		LListListIterator(LList<T> theList, LListItem<T> first) {
			super(first);
			this.theList = theList;
			this.previous = first.previous;
		}

		@Override
		public void add(T e) {
			this.theList.add(this.theList.indexOf(this.current.data), e);
		}
		
		@Override
		public boolean hasPrevious() {
			return (this.previous != null);
		}

		@Override
		public T next() {
			this.previous = this.current;
			if (null == this.previous) {
				this.previous = this.next;
			}
			return super.next();
		}

		@Override
		public int nextIndex() {
			if (this.current == null) {
				return 0;
			}
			if (null == this.current.next) {
				return this.theList.size();
			}
			int result = this.theList.indexOf(this.current.next.data);
			if (0 > result) {
				throw new IllegalStateException();
			}
			return result;
		}

		@Override
		public T previous() {
			if (!this.hasPrevious()) {
				throw new NoSuchElementException();
			}
			LListItem<T> result = this.previous;
			this.current = result;
			this.next = result.next;
			this.previous = result.previous;
			return result.data;
		}
		@Override
		public int previousIndex() {
			if (this.current == null) {
				return this.theList.size() - 1;
			}
			if (null == this.current.previous) {
				return -1;
			}
			int result = this.theList.indexOf(this.current.previous.data);
			if (0 > result) {
				throw new IllegalStateException();
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void set(T e) {
			this.current = new LListItem<T>(e, this.current.previous, this.current.next);
			if (null == this.current.previous) {
				this.theList.first = (LList<T>.LListItem<T>) this.current;
			} else {
				this.current.previous.setNext(this.current);
			}
			if (null == this.current.next) {
				this.theList.last = (LList<T>.LListItem<T>) this.current;
			} else {
				this.next.setPrevious(this.current);
			}
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
	public boolean add(E e) {
		return this.push(e);
	}

	@Override
	public void add(int index, E element) {
		if (0 > index || index > this.elementCount) {
			throw new IndexOutOfBoundsException();
		}
		if (index == this.elementCount) {
			this.push(element);
		} else {
			LListItem<E> follower = this.getListItem(index);
			LListItem<E> insert = new LListItem<>(element);
			insert.setPrevious(follower.setPrevious(insert));
			if (null == insert.previous) {
				insert.setNext(follower);
			} else {
				insert.setNext(insert.previous.setNext(insert));
			}
			++this.elementCount;
			if (index == 0) {
				this.first = insert;
			}
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return this.addAll(this.elementCount, c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		for (E element : c) {
			try {
				this.add(index++, element);
			} catch (Exception ex) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void clear() {
		for (E element : this) {
			this.remove(element);
		}
		this.first = null;
		this.last = null;
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
	public boolean containsAll(Collection<?> c) {
		for (E element : this) {
			if (!c.contains((Object) element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (null == o) {
			return false;
		}
		if (this == o) {
			return true;
		}
		try {
			@SuppressWarnings("unchecked")
			Collection<E> c = (Collection<E>)o;
			if (c.size() != this.size()) {
				return false;
			}
			for (E element : this) {
				if (!c.contains(element)) {
					return false;
				}
			}
			for (E element : c) {
				if (!this.contains(element)) {
					return false;
				}
			}
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	@Override
	public E get(int index) {
		return this.getListItem(index).data;
	}

	protected LListItem<E> getListItem(int index) {
		if (0 > index || index >= this.elementCount) {
			throw new IndexOutOfBoundsException();
		}
		LListItem<E> current = this.first;
		for (int i = 1; i <= index; ++i) {
			current = current.next;
			if (current == null) {
				throw new IllegalStateException();
			}
		}
		return current;
	}

	@Override
	public int indexOf(Object o) {
		int result = -1;
		boolean found = false;
		for (E element : this) {
			++result;
			if (element.equals(o)) {
				found = true;
				break;
			}
		}
		if (found) {
			return result;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return (this.elementCount == 0);
	}

	@Override
	public Iterator<E> iterator() {
		return new LListIterator<E>(this.first);
	}

	@Override
	public int lastIndexOf(Object o) {
		int result;
		boolean found = false;
		LListItem<E> current = this.last;
		for (result = this.elementCount - 1; current != null; --result) {
			if (current.data.equals(o)) {
				found = true;
				break;
			}
			current = current.previous;
		}
		if (found) {
			return result;
		}
		return -1;
	}

	@Override
	public ListIterator<E> listIterator() {
		return this.listIterator(0);
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new LListListIterator<E>(this, this.getListItem(index));
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
		--this.elementCount;
		return item.data;
	}

	@Override
	public boolean remove(Object o) {
		int index = this.indexOf(o);
		if (-1 == index) {
			return false;
		}
		this.remove(index);
		return true; 
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = false;
		for (Object o : c) {
			if (this.remove(o)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean result = false;
		for (Object o : this) {
			if (!c.contains(o)) {
				result = true;
				this.remove(o);
			}
		}
		return result;
	}

	@Override
	public E set(int index, E element) {
		if (0 > index || index >= this.elementCount) {
			throw new IndexOutOfBoundsException();
		}
		LListItem<E> node = this.getListItem(index);
		if (null == node) {
			throw new IllegalStateException();
		}
		E result = node.get();
		LListItem<E> insert = new LListItem<E>(element, node.previous, node.next);
		if (this.first == node) {
			this.first = insert;
		} else {
			node.previous.setNext(insert);
		}
		if (this.last == node) {
			this.last = insert;
		} else {
			node.next.setPrevious(insert);
		}
		return result;
	}

	@Override
	public int size() {
		return this.elementCount;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		LList<E> result = new LList<E>();
		for (int i = fromIndex; i <= toIndex; ++i) {
			result.add(this.get(i));
		}
		return result;
	}

	@Override
	public Object[] toArray() {
		return this.toArray(new Object[this.elementCount]);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < this.elementCount) {
			a = (T[]) Array.newInstance(a.getClass().getComponentType(), this.elementCount);
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
	public String toString() {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (E element : this) {
			if (first) {
				first = false;
			} else {
				result.append(" <-> ");
			}
			if (null == element) {
				result.append("<null>");
			} else {
				result.append(element.toString());
			}
		}
		return result.toString();
	}
	
}
