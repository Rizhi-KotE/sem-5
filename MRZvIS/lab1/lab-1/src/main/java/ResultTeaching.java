public class ResultTeaching {
    public double E;
    public int epochNaumber;
    public long time;
    public int N;
    public int p;
    public int L;
    public double e;
    public double a;

    public double getE() {
        return E;
    }

    public void setE(double e) {
        E = e;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public int getEpochNaumber() {
        return epochNaumber;
    }

    public void setEpochNumber(int steps) {
        this.epochNaumber = steps;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public ResultTeaching(double e, int epochNaumber, long time, int n, int p, int l, double e1, double a) {
        E = e;
        this.epochNaumber = epochNaumber;
        this.time = time;
        N = n;
        this.p = p;
        L = l;
        this.e = e1;
        this.a = a;
    }
}
