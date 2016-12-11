package rk.activemodel;

import java.sql.Date;

public class MeasurementIndividualMPD {

    private final double backgroundConcentration;
    private final double concentrationInEffluent;

    private final Outlet outlet;
    private final Substance substance;
    private final Alignment alignment;
    private final long id;
    private final double waste;
    private final Date date;

    MeasurementIndividualMPD(long id, Outlet outlet,
                             Substance substance,
                             Alignment alignment,
                             double backgroundConcentration,
                             double concentrationInEffluent, double waste,
                             Date date) {
        this.id = id;
        this.outlet = outlet;
        this.substance = substance;
        this.alignment = alignment;
        this.backgroundConcentration = backgroundConcentration;
        this.concentrationInEffluent = concentrationInEffluent;
        this.waste = waste;
        this.date = date;
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public Substance getSubstance() {
        return substance;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public double getDiameter() {
        return outlet.getDiameter();
    }

    public double getFlowRate() {
        return outlet.getFlowRate();
    }

    public double getWaste() {
        return waste;
    }

    public double getAngle() {
        return outlet.getAngle();
    }

    public double getDepth() {
        return outlet.getDepth();
    }

    public double getDistanceToCoast() {
        return outlet.getDistanceToCoast();
    }

    public double getDistanceToAlignment() {
        return outlet.getDistance() - alignment.getDistance();
    }

    public double getBackgroundConcentration() {
        return backgroundConcentration;
    }

    public double getConcentrationInEffluent() {
        return concentrationInEffluent;
    }

    public Date getDate() {
        return date;
    }

    public double getNSC() {
        return alignment.getNSC(substance);
    }

    public double getMPC() {
        return alignment.getMPC(substance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasurementIndividualMPD mpd = (MeasurementIndividualMPD) o;

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

    public long getId() {
        return id;
    }
}
