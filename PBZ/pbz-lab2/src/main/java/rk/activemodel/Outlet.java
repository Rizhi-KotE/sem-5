package rk.activemodel;

public class Outlet {
    private final long id;
    private final double diameter;
    private final double flowRate;
    private final double waste;
    private final double angle;
    private final double depth;
    private final double distanceToCoast;
    private final double distance;

    public double getDiameter() {
        return diameter;
    }

    public double getFlowRate() {
        return flowRate;
    }

    public double getWaste(Substance substance) {
        return waste;
    }

    public double getAngle() {
        return angle;
    }

    public double getDepth() {
        return depth;
    }

    public double getDistanceToCoast() {
        return distanceToCoast;
    }

    public Outlet(long outlet_id, double diameter, double flowRate, double waste, double angle, double depth, double distanceToCoast, double distance) {

        id = outlet_id;
        this.diameter = diameter;
        this.flowRate = flowRate;
        this.waste = waste;

        this.angle = angle;
        this.depth = depth;
        this.distanceToCoast = distanceToCoast;
        this.distance = distance;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Outlet outlet = (Outlet) o;

        if (id != outlet.id) return false;
        if (Double.compare(outlet.diameter, diameter) != 0) return false;
        if (Double.compare(outlet.flowRate, flowRate) != 0) return false;
        if (Double.compare(outlet.waste, waste) != 0) return false;
        if (Double.compare(outlet.angle, angle) != 0) return false;
        if (Double.compare(outlet.depth, depth) != 0) return false;
        return Double.compare(outlet.distanceToCoast, distanceToCoast) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        temp = Double.doubleToLongBits(diameter);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(flowRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(waste);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(angle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(depth);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(distanceToCoast);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public double getDistance() {
        return distance;
    }
}
