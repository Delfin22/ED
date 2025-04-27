package com.ed.ed;

//Klasa do zgrabnego sortowania punktów ROC
public class ROCPair implements Comparable<ROCPair>{
    private boolean prawdziwaWartosc;
    private double prawdopodobienstwo;

    public ROCPair(boolean prawdziwaWartosc, double prawdopodobienstwo) {
        this.prawdziwaWartosc = prawdziwaWartosc;
        this.prawdopodobienstwo = prawdopodobienstwo;
    }

    public boolean getPrawdziwaWartosc() {
        return prawdziwaWartosc;
    }

    public void setPrawdziwaWartosc(boolean prawdziwaWartosc) {
        this.prawdziwaWartosc = prawdziwaWartosc;
    }

    public double getPrawdopodobienstwo() {
        return prawdopodobienstwo;
    }

    public void setPrawdopodobienstwo(double prawdopodobienstwo) {
        this.prawdopodobienstwo = prawdopodobienstwo;
    }

    @Override
    public int compareTo(ROCPair o) {
        return Double.compare(this.prawdopodobienstwo,o.prawdopodobienstwo);
    }
}
