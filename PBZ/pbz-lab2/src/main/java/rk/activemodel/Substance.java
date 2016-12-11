package rk.activemodel;

public class Substance {
    private final long id;
    private final String substanceName;

    public Substance(long id, String name) {
        this.id = id;
        this.substanceName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Substance substance = (Substance) o;

        if (id != substance.id) return false;
        return substanceName != null ? substanceName.equals(substance.substanceName) : substance.substanceName == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (substanceName != null ? substanceName.hashCode() : 0);
        return result;
    }

    public String getName() {

        return substanceName;
    }

    public long getId() {
        return id;
    }
}
