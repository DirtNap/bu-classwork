package edu.bu.cs342.p03;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public abstract class CollectionHash<E> {
    protected static class DummyOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            return;
        }
    }

    public abstract boolean add(E item);

    public abstract E delete(E item);

    public abstract void showHash(PrintStream output);

    public abstract int traceSearch(E item, PrintStream output);
    
    public abstract int size();

    public boolean contains(E item) {
        return (-1 != this.search(item));
    }

    public int search(E item) {
        return this.traceSearch(item, new PrintStream(new DummyOutputStream()));
    }

}
