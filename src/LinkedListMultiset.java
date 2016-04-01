import java.io.PrintStream;
import java.util.*;

/**
 * Framework to test the multiset implementations.
 *
 * @author jkcchan'
 *
 * Modified by Loy Rao
 */
public class LinkedListMultiset<T> extends Multiset<T>
{
	private Node<T> mHead;
	private Comparator mComparator;
	
	// Default constructor
	public LinkedListMultiset() 
	{
		mHead = null;
		mComparator = new CompareString();
	}
	
	// Constructor to that takes comparator to allow items of other classes
	public LinkedListMultiset(Comparator comparator) 
	{
		mHead = null;
		mComparator = comparator;
	}
	
	
	public void add(T item) 
	{
		Node<T> searchResult = null;
		if(mHead == null)
		{
			mHead = new Node<T>(item,1);
		}
		else
		{
			searchResult = this.searchNode(item);
			if(searchResult == null) // Item does not exist
			{	
				Node<T> newNode = new Node<T>(item,1);
				newNode.setNext(mHead);
				mHead = newNode;
			}
			else // Item exists just incrementing count
				searchResult.setCount(searchResult.getCount()+1);
		}
		
	}
	
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
				if(mComparator.compare(currNode.getValue(), item) == 0) //item found
					return currNode.getCount();
				currNode = currNode.getNext();
			}
			while(currNode != null);
		}
		return 0;
	} 
	
	//Added search function to return node after searching item for add()
	public Node<T> searchNode(T item) 
	{		
		if(mHead == null)
			return null;
		else
		{
			Node<T> currNode = mHead;
			do
			{
				if(mComparator.compare(currNode.getValue(), item) == 0)
					return currNode;
				currNode = currNode.getNext();
			}
			while(currNode != null);
		}
		return null;
	}
	
	
	public void removeOne(T item) 
	{
		if(mHead != null)
		{
			Node<T> currNode = mHead;
			Node<T> prevNode = null;
			do
			{
				if(mComparator.compare(currNode.getValue(), item) == 0)
				{
					if(prevNode == null)
					{
						if(currNode.getCount() == 1) //Count is one, so remove node
							mHead = mHead.getNext();
						else //Reduce count by one
							currNode.setCount(currNode.getCount()-1);
						break;
					}
					else
					{
						if(currNode.getCount() == 1) //Count is one, so remove node
							prevNode.setNext(currNode.getNext());
						else //Reduce count by one
							currNode.setCount(currNode.getCount()-1);
						break;
					}
				}
				prevNode = currNode;
				currNode = currNode.getNext();
			}
			while(currNode != null);
		}
	}
	
	
	public void removeAll(T item) 
	{
		if(mHead != null)
		{
			Node<T> currNode = mHead;
			Node<T> prevNode = null;
			do
			{
				if(mComparator.compare(currNode.getValue(), item) == 0)
				{
					if(prevNode == null) // Head matched item
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
	}
	
	
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
	}
	
	public class Node<T>
	{
		private T mValue; // Value of Node
		private int mCount; // Count of Value
	    private Node<T> mNext; // Reference to next node

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
	
	public class CompareString implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if (o1.compareTo(o2) < 0) {
                return -1;
            } else if (o1.compareTo(o2) == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }

	
} // end of class LinkedListMultiset