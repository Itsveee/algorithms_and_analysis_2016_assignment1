import java.io.PrintStream;
import java.util.*;

public class LinkedListMultiset<T extends Comparable<T>> extends Multiset<T>
{
	Node<T> mHead;
	public LinkedListMultiset() 
	{
		mHead = null;
	} // end of LinkedListMultiset()
	
	
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
				newNode.setNext(mHead);
				mHead = newNode;
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
							currNode.setCount(currNode.getCount()-1);
					}
					else
					{
						if(currNode.getCount() == 1)
						{
							prevNode.setNext(currNode.getNext());
						}
						else
							currNode.setCount(currNode.getCount()-1);
					}
				}
				prevNode = currNode;
				currNode = currNode.getNext();
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
					}
				}
				prevNode = currNode;
				currNode = currNode.getNext();
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

	
} // end of class LinkedListMultiset