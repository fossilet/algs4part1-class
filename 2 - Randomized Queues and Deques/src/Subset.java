/****************************************************************************
 * Author: Alberto Gutiérrez Jácome <agjacome@gmail.com>
 * Date: 28/08/2012
 * 
 * Compilation: javac Subset.java
 * Execution: java Subset N
 * Dependencies: RandomizedQueue.java StdIn.java StdOut.java
 * 
 * This class implements client code for a subset program. It takes a
 * command-line integer k, reads in a sequence of N strings from standard
 * input and prints out exactly k of them, uniformly at random. Each item from
 * the sequence is printed out at most once. It is assumed that k >= 0 and no
 * greater than the number of strings on standard input.
 * 
 * Usage examples:
 * % echo A B C D E F G H I | java Subset 3
 * % echo A B C D E F G H I | java Subset 6
 * % echo AA BB BB BB BB BB CC CC | java Subset 8
 * 
 ***************************************************************************/

public class Subset
{

    // main method, implements the subset client
    public static void main(String[ ] args)
    {
        RandomizedQueue<String> str = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
            str.enqueue(StdIn.readString());

        for (int i = 0; i < Integer.parseInt(args[0]); i++)
            StdOut.println(str.dequeue());
    }

}
