package org.example;

public class IntPair {
    final Integer x;
    final Integer y;

    IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!x.equals(((IntPair)obj).x)) {
            return false;
        }
        return y.equals(((IntPair)obj).y);
    }

    @Override
    public int hashCode() {
        return 31 * x.hashCode() + y.hashCode();
    }
}
