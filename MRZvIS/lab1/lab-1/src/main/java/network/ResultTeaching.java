package network;

import lombok.Data;

@Data
public class ResultTeaching {
    public double E;
    public int epochNumber;
    public long time;
    public int N;
    public int p;
    public int L;
    public double e;
    public double a;

    public ResultTeaching(double e, int epochNumber, long time, int n, int p, int l, double e1, double a) {
        E = e;
        this.epochNumber = epochNumber;
        this.time = time;
        N = n;
        this.p = p;
        L = l;
        this.e = e1;
        this.a = a;
    }
}
