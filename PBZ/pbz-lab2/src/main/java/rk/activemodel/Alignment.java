package rk.activemodel;

public class Alignment {
    private final long id;
    private final double distance;

    public Alignment(long id, double distance) {
        this.id = id;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Alignment alignment = (Alignment) o;

        if (id != alignment.id) return false;
        return Double.compare(alignment.distance, distance) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(distance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public long getId() {
        return id;
    }

    public double getDistance() {
        return distance;
    }

    public double getMPC(Substance substance) {
        return 0;
    }

    public double getNSC(Substance substance) {
        return 0;
    }
}
