/****************************************************************************
 * Author: Alberto Gutiérrez Jácome <agjacome@gmail.com>
 * Date: 28/08/2012
 * 
 * Compilation: javac RandomizedQueue.java
 * Execution: not applicable
 * Dependencies: StdRandom.java
 * 
 * This class implements a randomized queue using a automatic resizing array.
 * A randomized queue is similar to a stack or queue, except that the item
 * removed is chosen uniformly at random from items in the data structure.
 * This implementation supports each randomized queue operation (besides
 * creating an iterator) in constant amortized time and uses space
 * proportional to the number of items currently in the queue.
 * 
 ***************************************************************************/

import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item>
{

    private class RandQueueIterator implements Iterator<Item>
    {

        private int current = 0;
        private Item[ ] shuffled;

        @SuppressWarnings("unchecked")
        public RandQueueIterator( )
        {
            shuffled = (Item[ ]) new Object[size];
            for (int i = head % queue.length; i < size; i++)
                shuffled[i - (head % queue.length)] = queue[i];

            StdRandom.shuffle(shuffled);
        }

        public boolean hasNext( )
        {
            return current < size;
        }

        public Item next( )
        {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return shuffled[current++];
        }

        public void remove( )
        {
            throw new java.lang.UnsupportedOperationException();
        }
    }

    private Item[ ] queue;   // the actual queue array

    private int head = 0;  // position of first element in array
    private int size = 0;  // size of the queue array
    private int tail = 0;  // position of last element in array

    // construct an empty randomized queue
    @SuppressWarnings("unchecked")
    public RandomizedQueue( )
    {
        queue = (Item[ ]) new Object[2];
    }

    // delete and return a random item
    public Item dequeue( )
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        exchange(head, (StdRandom.uniform(size) + head) % queue.length);
        final Item item = queue[head];
        queue[head] = null;
        size--;
        head++;
        if (head == queue.length)
            head = 0;
        if (size > 0 && size == queue.length / 4)
            resize(queue.length / 2);

        return item;
    }

    // add the item
    public void enqueue(final Item item)
    {
        if (item == null)
            throw new java.lang.NullPointerException();

        if (size == queue.length)
            resize(2 * queue.length);
        queue[tail++] = item;
        if (tail == queue.length)
            tail = 0;
        size++;
    }

    // exchange two queue array positions
    private void exchange(final int i, final int j)
    {
        final Item swap = queue[i];
        queue[i] = queue[j];
        queue[j] = swap;
    }

    // is the queue empty?
    public boolean isEmpty( )
    {
        return size == 0;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator( )
    {
        return new RandQueueIterator();
    }

    // resizes the current queue capacity to a new given one
    private void resize(final int capacity)
    {
        assert capacity >= size;

        @SuppressWarnings("unchecked")
        final Item[ ] copy = (Item[ ]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = queue[(head + i) % queue.length];

        queue = copy;
        head = 0;
        tail = size;
    }

    // return (but do not delete) a random item
    public Item sample( )
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        return queue[(StdRandom.uniform(size) + head) % queue.length];
    }

    // return the number of items on the queue
    public int size( )
    {
        return size;
    }

}
