import java.io.PrintStream;
import java.util.Comparator;

public class BstMultiset<T> extends Multiset<T> {

    private Node mRoot;
    private Comparator mComparator;

	public BstMultiset() {
        mRoot = null;
        // If no comparator is supplied assume String.
        // String is what's used in the testing for the "assignment", but that doesn't mean we can't implement for
        // other hypothetical test classes that pass in a comparator. That way it's generic.
        mComparator = new CompareString();

	} // end of BstMultiset()

    public BstMultiset(Comparator comparator) {
        mRoot = null;
        mComparator = comparator;
    }

	public void add(T item) {
        mRoot = add(mRoot, item);
    } // end of add()

    public Node add(Node root, T item) {
        return add(root, item, 1);
    }

    public Node add(Node root, T item, int count) {
        int compare;

        if (root == null) {
            Node node = new Node(item);
            node.setCount(count);
            return node;
        }
        compare = mComparator.compare(item, root.getItem());
        if (compare == 0) {
            // Item exists. Just increment count.
            root.incrementCount();
        } else if (compare < 0) {
            // Item is smaller than root, add to the left branch
            root.setLeft(add(root.getLeft(), item));
        } else {
            // Item is larger than root, add to the right branch
            root.setRight(add(root.getRight(), item));
        }
        return root;
    }

	public int search(T item) {
        Node node = lookup(mRoot, item);
        if (node == null) {
            return 0;
        } else {
            return node.getCount();
        }
    } // end of add()

    /*
    * Lookup find a node and returns it.
    *
    * */
    public Node lookup(Node root, T item) {
        int compare;

        while (root != null) {
            compare = mComparator.compare(item, root.getItem());
            if (compare == 0) {
                return root;
            } else if (compare < 0) {
                root = root.getLeft();
            } else {
                root = root.getRight();
            }
        }
        return null;
    }

	public void removeOne(T item) {
        Node node = lookup(mRoot, item);

        if (node != null) {
            node.decrementCount();
            if (node.getCount() == 0) {
                // Need to also remove entire Node
                removeNode(mRoot, item);
            }
        }
    } // end of removeOne()

    public void removeAll(T item) {
        Node node = lookup(mRoot, item);

        if (node != null) {
            // For removeAll just immediately remove the entire node
            removeNode(mRoot, item);
        }
    } // end of removeAll()

    public void removeNode(Node root, T item) {
        Node parentNode = null;
        Node curLeftNode;
        Node curRightNode;
        int compare;

        while (root != null) {
            compare = mComparator.compare(item, root.getItem());
            if (compare == 0) {
                if (parentNode == null) {
                    // need to remove actual root node
                    if (root.getRight() != null) {
                        curRightNode = root.getRight();
                        if (root.getLeft() != null) {
                            mRoot = root.getLeft();
                            // need to insert the right node into the left subtree
                            add(mRoot, curRightNode.getItem(), curRightNode.getCount());
                        } else {
                            mRoot = root.getRight();
                        }
                    } else if (root.getLeft() != null) {
                        mRoot = root.getLeft();
                    } else {
                        mRoot = null;
                    }
                } else {
                    int compare2 = mComparator.compare(item, parentNode.getItem());
                    if (compare2 < 0) {
                        // item is in the left tree of it's parent
                        curRightNode = root.getRight();
                        parentNode.setLeft(root.getLeft());
                        if (curRightNode != null) {
                            add(mRoot, curRightNode.getItem(), curRightNode.getCount());
                        }
                    } else if (compare2 > 0) {
                        // item is in the right tree of it's parent
                        curLeftNode = root.getLeft();
                        parentNode.setRight(root.getRight());
                        if (curLeftNode != null) {
                            add(mRoot, curLeftNode.getItem(), curLeftNode.getCount());
                        }
                    }
                }
                return;
            } else if (compare < 0) {
                parentNode = root;
                root = root.getLeft();
            } else {
                parentNode = root;
                root = root.getRight();
            }
        }
    }

	public void print(PrintStream out) {
        applyInOrder(mRoot, out);
    } // end of print()

    public void applyInOrder(Node root, PrintStream out) {
        if (root == null) {
            return;
        }
        applyInOrder(root.getLeft(), out);
        out.println(root.getItem() + printDelim + root.getCount());
        applyInOrder(root.getRight(), out);
    }

    /*
    * Node type class used specifically for the binary search tree implementation.
    *
    * */
    public class Node {
        private T mItem;
        private Node mLeft;
        private Node mRight;
        private int mCount;

        public Node(T item) {
            mItem = item;
            mLeft = null;
            mRight = null;
            mCount = 1;
        }

        public T getItem() {
            return mItem;
        }

        public void setLeft(Node node) {
            mLeft = node;
        }

        public Node getLeft() {
            return mLeft;
        }

        public void setRight(Node node) {
            mRight = node;
        }

        public Node getRight() {
            return mRight;
        }

        public void incrementCount() {
            mCount++;
        }

        public void decrementCount() {
            mCount--;
        }

        public void setCount(int count) {
            mCount = count;
        }

        public int getCount() {
            return mCount;
        }
    }

    /*
    * Comparator class used by the current test classes and the default.
    *
    * */
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

    /*
    * Example of another comparator class. Not actually used, just to show how another one might look.
    *
    * */
    public class CompareInt implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 < o2) {
                return -1;
            } else if (o1.equals(o2)) {
                return 0;
            } else {
                return 1;
            }
        }
    }
} // end of class BstMultiset
