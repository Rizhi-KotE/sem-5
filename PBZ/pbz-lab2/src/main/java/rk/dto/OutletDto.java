package rk.dto;

import rk.activemodel.Company;
import rk.activemodel.Outlet;

public class OutletDto {
    public long outletId;
    public double diameter;
    public double flowRate;
    public double waste;
    public double angle;
    public double depth;
    public double distanceToCoast;
    public double distance;
    public long companyId;

    public OutletDto() {
    }

    public OutletDto(Outlet outlet) {
        outletId = outlet.getId();

        diameter = outlet.getDiameter();
        flowRate = outlet.getFlowRate();
        waste = outlet.getWaste();
        angle = outlet.getAngle();
        depth = outlet.getDepth();
        distanceToCoast = outlet.getDistanceToCoast();
        distance = outlet.getDistance();
        companyId = outlet.getCompany().getId();

    }

    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(long outletId) {
        this.outletId = outletId;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(double flowRate) {
        this.flowRate = flowRate;
    }

    public double getWaste() {
        return waste;
    }

    public void setWaste(double waste) {
        this.waste = waste;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public double getDistanceToCoast() {
        return distanceToCoast;
    }

    public void setDistanceToCoast(double distanceToCoast) {
        this.distanceToCoast = distanceToCoast;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
