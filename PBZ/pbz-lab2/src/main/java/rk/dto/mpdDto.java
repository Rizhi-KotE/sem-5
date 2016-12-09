package rk.dto;

import rk.activemodel.Alignment;
import rk.activemodel.Outlet;
import rk.activemodel.Substance;

import java.sql.Date;

public class mpdDto {
    public long outletId;
    public long substanceId;
    public long alignmentId;
    public double backgroundConcentration;
    public double concentrationEffluent;
    public double waste;
    public long date;

    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(long outletId) {
        this.outletId = outletId;
    }

    public long getSubstanceId() {
        return substanceId;
    }

    public void setSubstanceId(long substanceId) {
        this.substanceId = substanceId;
    }

    public long getAlignmentId() {
        return alignmentId;
    }

    public void setAlignmentId(long alignmentId) {
        this.alignmentId = alignmentId;
    }

    public double getBackgroundConcentration() {
        return backgroundConcentration;
    }

    public void setBackgroundConcentration(double backgroundConcentration) {
        this.backgroundConcentration = backgroundConcentration;
    }

    public double getConcentrationEffluent() {
        return concentrationEffluent;
    }

    public void setConcentrationEffluent(double concentrationEffluent) {
        this.concentrationEffluent = concentrationEffluent;
    }

    public double getWaste() {
        return waste;
    }

    public void setWaste(double waste) {
        this.waste = waste;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
