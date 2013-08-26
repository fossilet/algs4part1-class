/****************************************************************************
 * Author: Alberto Gutiérrez Jácome <agjacome@gmail.com>
 * Date: 28/08/2012
 * 
 * Compilation: javac Deque.java
 * Execution: not applicable
 * Dependencies: none aside from java libs
 * 
 * This class implements a double-ended queue (deque) using a doubly linked
 * list. A deque is a generalization of a stack and a queue that supports
 * inserting and removing items from either the front or the back of the data
 * structure. This implementation supports each deque operation in constant
 * worst-case time and uses space proportional to the number of items
 * currently in the deque.
 * 
 ***************************************************************************/

import java.util.Iterator;


public class Deque<Item> implements Iterable<Item>
{

    // class to implement the deque iterator
    private class DequeIterator implements Iterator<Item>
    {

        private DequeNode<Item> current = head.next;

        public boolean hasNext( )
        {
            return current != tail;
        }

        public Item next( )
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            final Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove( )
        {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    // class to store each deque node
    private static class DequeNode<Item>
    {

        private Item item;    // the actual item
        private DequeNode<Item> next;    // pointer to next node
        private DequeNode<Item> prev;    // pointer to previous node
    }

    private int size;       // size of the deque
    private final DequeNode<Item> head;       // first node (sentinel)
    private final DequeNode<Item> tail;       // last node (sentinel)

    // construct an empty deque
    public Deque( )
    {
        size = 0;
        head = new DequeNode<Item>();
        tail = new DequeNode<Item>();
        head.next = tail;
        tail.prev = head;
    }

    // insert the item at the front
    public void addFirst(final Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException();

        final DequeNode<Item> frst = head.next;
        final DequeNode<Item> node = new DequeNode<Item>();
        node.item = item;
        node.next = frst;
        node.prev = head;
        head.next = node;
        frst.prev = node;
        size++;
    }

    // insert the item at the end
    public void addLast(final Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException();

        final DequeNode<Item> last = tail.prev;
        final DequeNode<Item> node = new DequeNode<Item>();
        node.item = item;
        node.next = tail;
        node.prev = last;
        tail.prev = node;
        last.next = node;
        size++;
    }

    // is the deque empty?
    public boolean isEmpty( )
    {
        return size == 0;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator( )
    {
        return new DequeIterator();
    }

    // delete and return the item at the front
    public Item removeFirst( )
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        final DequeNode<Item> frst = head.next;
        final DequeNode<Item> next = frst.next;
        next.prev = head;
        head.next = next;
        size--;

        return frst.item;
    }

    // delete and return the item at the end
    public Item removeLast( )
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        final DequeNode<Item> last = tail.prev;
        final DequeNode<Item> prev = last.prev;
        tail.prev = prev;
        prev.next = tail;
        size--;

        return last.item;
    }

    // return the number of items on the deque
    public int size( )
    {
        return size;
    }

}
