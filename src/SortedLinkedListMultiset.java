import java.io.PrintStream;
import java.util.*;

public class SortedLinkedListMultiset<T extends Comparable<T>> extends Multiset<T>
{
	Node<T> mHead;
	
	public SortedLinkedListMultiset() 
	{	
		mHead = null;
	} // end of SortedLinkedListMultiset()
	
	
	public void add(T item) 
	{
		int count = 0;
		if(mHead == null)
		{
			mHead = new Node<T>(item,1);
		}
		else
		{
			count = this.search(item);
			if(count == 0)
			{	
				Node<T> newNode = new Node<T>(item,1);
				Node<T> currNode = mHead;
				Node<T> prevNode = null;
				while(currNode != null && currNode.getValue().compareTo(item) < 0) 	
				{
					prevNode = currNode;
					currNode = currNode.getNext();
				}
				if(currNode == null)
					prevNode.setNext(newNode);
				else if(prevNode == null)
				{
					newNode.setNext(mHead);
					mHead = newNode;
				}
				else
				{
					newNode.setNext(currNode);
					prevNode.setNext(newNode);
				}
			}
			else
			{
				Node<T> currNode = mHead;
				do
				{
					if(currNode.getValue().equals(item))
					{
						currNode.setCount(currNode.getCount()+1);
						break;
					}
					currNode = currNode.getNext();
				}
				while(currNode != null);
			}
		}
	
	} // end of add()
	
	
	public int search(T item) 
	{		
		int count = 0;		
		if(mHead == null)
			return count;
		else
		{
			Node<T> currNode = mHead;
			do
			{
				if(currNode.getValue().equals(item))
					return currNode.getCount();
				currNode = currNode.getNext();
			}
			while(currNode != null);
		}
		// default return, please override when you implement this method
		return 0;
	} // end of add()
	
	
	public void removeOne(T item) 
	{
		if(mHead != null)
		{
			Node<T> currNode = mHead;
			Node<T> prevNode = null;
			do
			{
				if(currNode.getValue().equals(item))
				{
					if(prevNode == null)
					{
						if(currNode.getCount() == 1)
						{
							mHead = mHead.getNext();
							break;
						}
						else
						{
							currNode.setCount(currNode.getCount()-1);
							break;
						}
					}
					else
					{
						if(currNode.getCount() == 1)
							prevNode.setNext(currNode.getNext());
						else
							currNode.setCount(currNode.getCount()-1);
						break;
					}
				}
				prevNode = currNode;
				currNode = currNode.getNext();
				if(currNode.getValue().compareTo(item) > 0)
				{
					// Quit traversing to improve efficiency
					break;
				}
			}
			while(currNode != null);
		}
	} // end of removeOne()
	
	
	public void removeAll(T item)
	{
		if(mHead != null)
		{
			Node<T> currNode = mHead;
			Node<T> prevNode = null;
			do
			{
				if(currNode.getValue().equals(item))
				{
					if(prevNode == null)
					{
						mHead = mHead.getNext();
						break;
					}
					else
					{
						prevNode.setNext(currNode.getNext());
						break;
					}
				}
				prevNode = currNode;
				currNode = currNode.getNext();
				if(currNode.getValue().compareTo(item) > 0)
				{
					System.out.println("This value does not exists: " + currNode.getValue().compareTo(item));
					break;
				}
			}
			while(currNode != null);
		}
	} // end of removeAll()
	
	
	public void print(PrintStream out) 
	{
		Node<T> currNode = mHead;
		if(currNode != null)
		{
			do
			{
				out.println(currNode.getValue() + printDelim + currNode.getCount());
				currNode = currNode.getNext();
			}
			while(currNode != null);
		}
	} // end of print()
	
	public class Node<T extends Comparable<T>>
	{
		 /** Stored value of node. */
		private T mValue;
		private int mCount;
		/** Reference to next node. */
	    private Node<T> mNext;

	    public Node(T value, int count) 
	    {
	        mValue = value;
	        mCount = count;
	        mNext = null;
	    }
	    
	    public int getCount()
		{
			return mCount;
		}

		public void setCount(int mCount)
		{
			this.mCount = mCount;
		}

	    public T getValue() 
	    {
	        return mValue;
	    }

	    public Node<T> getNext() 
	    {
	        return mNext;
	    }

	    public void setValue(T value) 
	    {
	        mValue = value;
	    }

	    public void setNext(Node<T> next) 
	    {
	        mNext = next;
	    }
		
	}

} // end of class SortedLinkedListMultiset

