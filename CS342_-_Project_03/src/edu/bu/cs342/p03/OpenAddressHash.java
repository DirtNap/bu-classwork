package edu.bu.cs342.p03;

import java.io.PrintStream;

public class OpenAddressHash<E> extends CollectionHash<E> {

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            OpenAddressHash test = (OpenAddressHash) o;
            // TODO Perform OpenAddressHash specific comparison
        } catch (ClassCastException ex) {
            return false;
        }
        return false;
    }

    public int initialSize;

    public OpenAddressHash() {
        this(31);
    }

    public OpenAddressHash(int size) {
        this.initialSize = size;
    }

    @Override
    public boolean add(E item) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E delete(E item) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void showHash(PrintStream output) {
        // TODO Auto-generated method stub

    }

    @Override
    public int traceSearch(E item, PrintStream output) {
        // TODO Auto-generated method stub
        return -1;
    }
}
