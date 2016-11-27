package rk.activemodel;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;;

import java.util.Set;

public class Company {

    private JdbcTemplate template;

    private Outlets outlets;

    void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    void setOutlets(Outlets outlets) {
        this.outlets = outlets;
    }

    private final long id;
    private final String name;

    Company(long id, String name) {

        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Outlet> findAllOutlets() {
        return outlets.find();
    }

    public Outlet createNewOutlet(double diameter, double flowRate, double waste, double angle, double depth, double distanceToCoast) {
        return outlets.createNewOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast);
    }

    public Outlet findOutlet(long id) {
        return outlets.find(id);
    }
}
