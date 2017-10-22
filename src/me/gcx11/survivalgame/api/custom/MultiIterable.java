package me.gcx11.survivalgame.api.custom;

import java.util.Iterator;

/**
 * Created on 29.8.2017.
 */
public class MultiIterable<T> implements Iterable<T>  {

    private Iterable<T> first;
    private Iterable<T> second;

    /**
     * Creates new MultiIterable, which supports iterating over two collections at once.
     * @param first first iterable
     * @param second second iterable
     */
    public MultiIterable(Iterable<T> first, Iterable<T> second){
        this.first = first;
        this.second = second;
    }

    /**
     * Creates new MultiIterable from two iterables.
     * @param first first iterable
     * @param second second iterable
     * @return new MultiIterable
     */
    public static <U> MultiIterable<U> From(Iterable<U> first, Iterable<U> second){
        return new MultiIterable<>(first, second);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private boolean iteratingFirst = true;

            private Iterator<T> firstIter = first.iterator();
            private Iterator<T> secondIter = second.iterator();

            public boolean hasNext() {
                if (iteratingFirst){
                    if (firstIter.hasNext()) return true;
                    else{
                        iteratingFirst = false;
                    }
                }
                return secondIter.hasNext();
            }

            @Override
            public T next() {
                return iteratingFirst ? firstIter.next() : secondIter.next();
            }
        };
    }
}
