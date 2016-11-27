package rk.activemodel;

public class IndividualMPD {

    private final double backgroundConcentration;
    private final double concentrationInEffluent;

    private final Outlet outlet;
    private final Substance substance;
    private final Alignment alignment;
    private final long id;

    IndividualMPD(long id, Outlet outlet,
                  Substance substance,
                  Alignment alignment,
                  double backgroundConcentration,
                  double concentrationInEffluent) {
        this.id = id;
        this.outlet = outlet;
        this.substance = substance;
        this.alignment = alignment;
        this.backgroundConcentration = backgroundConcentration;
        this.concentrationInEffluent = concentrationInEffluent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndividualMPD mpd = (IndividualMPD) o;

        if (Double.compare(mpd.backgroundConcentration, backgroundConcentration) != 0) return false;
        if (Double.compare(mpd.concentrationInEffluent, concentrationInEffluent) != 0) return false;
        if (outlet != null ? !outlet.equals(mpd.outlet) : mpd.outlet != null) return false;
        if (substance != null ? !substance.equals(mpd.substance) : mpd.substance != null) return false;
        return alignment != null ? alignment.equals(mpd.alignment) : mpd.alignment == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(backgroundConcentration);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(concentrationInEffluent);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (outlet != null ? outlet.hashCode() : 0);
        result = 31 * result + (substance != null ? substance.hashCode() : 0);
        result = 31 * result + (alignment != null ? alignment.hashCode() : 0);
        return result;
    }

    public double getValue(){
        return  hashCode();
    }

    public long getId() {
        return id;
    }
}
