import java.io.PrintStream;
import java.util.*;

public class SortedLinkedListMultiset<T> extends Multiset<T>
{
	private Node<T> mHead;
	private Comparator mComparator;
	
	// Default Constructor
	public SortedLinkedListMultiset() 
	{	
		mHead = null;
		mComparator = new CompareString();
	}
	
	// Constructor to that takes comparator to allow items of other classes
	public SortedLinkedListMultiset(Comparator comparator) 
	{	
		mHead = null;
		mComparator = comparator;
	}
	
	
	public void add(T item) 
	{
		
		int count = 0;
		if(mHead == null)
		{
			mHead = new Node<T>(item,1);
		}
		else
		{
			Node<T> newNode = new Node<T>(item,1);
			Node<T> currNode = mHead;
			Node<T> prevNode = null;
			while(currNode != null && mComparator.compare(currNode.getValue(), item) < 0) 	
			{
				prevNode = currNode;
				currNode = currNode.getNext();
			}
			if(currNode == null)
				// Reached end of list, add at the end
				prevNode.setNext(newNode);
			else if(mComparator.compare(currNode.getValue(), item) == 0)
				// If already exists, just increment count
				currNode.setCount(currNode.getCount() + 1);
			else if(prevNode == null)
			{
				// Item goes at the beginning of the list
				newNode.setNext(mHead);
				mHead = newNode;
			}
			else
			{
				newNode.setNext(currNode);
				prevNode.setNext(newNode);
			}
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
//				if(mComparator.compare(currNode.getValue(), item) > 0)
//					// The item does not exist because current item is > than item
//					break;
				if(mComparator.compare(currNode.getValue(), item) == 0)
					return currNode.getCount();
				currNode = currNode.getNext();
				
			}
			while(currNode != null);
		}
		return 0;
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
					if(prevNode == null) // Root node contains item
					{
						if(currNode.getCount() == 1) // Last count, remove node
							mHead = mHead.getNext();
						else
							currNode.setCount(currNode.getCount()-1); // Reduce count by one
						break;
					}
					else 
					{
						if(currNode.getCount() == 1) // Last count, remove node
							prevNode.setNext(currNode.getNext());
						else
							currNode.setCount(currNode.getCount()-1); // Reduce count by one
						break;
					}
				}
//				if(mComparator.compare(currNode.getValue(), item) > 0)
//					// The item does not exist because current item is > than item
//					break;
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
					if(prevNode == null) // Root node has item
						mHead = mHead.getNext();
					else
						prevNode.setNext(currNode.getNext());
					break;
				}
//				if(mComparator.compare(currNode.getValue(), item) > 0)
//					// The item does not exist because current item is > than item
//					break;
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
		private T mValue; // Stored value of Node
		private int mCount; // Count of value
	    private Node<T> mNext; // Reference to next Node

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
} // end of class SortedLinkedListMultiset

