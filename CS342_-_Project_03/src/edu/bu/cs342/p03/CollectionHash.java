package edu.bu.cs342.p03;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class CollectionHash<E> {
    protected static class dummyOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            ;
        }
    }

    public abstract boolean add(E item);

    public abstract E delete(E item);

    public abstract void showHash(PrintStream output);

    public abstract E traceSearch(E item, PrintStream output);

    public boolean contains(E item) {
        return (null != this.search(item));
    }

    public E search(E item) {
        return this.traceSearch(item, new PrintStream(new dummyOutputStream()));
    }

}
