package rk.activemodel;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

;

public class Company {

    private final long id;
    private final String name;
    private JdbcTemplate template;
    private Outlets outlets;

    Company(long id, String name) {

        this.id = id;
        this.name = name;
    }

    void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    void setOutlets(Outlets outlets) {
        this.outlets = outlets;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != company.id) return false;
        if (outlets != null ? !outlets.equals(company.outlets) : company.outlets != null) return false;
        return name != null ? name.equals(company.name) : company.name == null;

    }

    @Override
    public int hashCode() {
        int result = outlets != null ? outlets.hashCode() : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Set<Outlet> findAllOutlets() {
        return outlets.find(this);
    }

    public Outlet createOutlet(double diameter,
                               double flowRate,
                               double waste,
                               double angle,
                               double depth,
                               double distanceToCoast,
                               double distanceOnWater) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .withTableName("outlets")
                .usingGeneratedKeyColumns("outlet_id");
        HashMap map = new HashMap();
        map.put("diameter", diameter);
        map.put("flow_rate", flowRate);
        map.put("waste", waste);
        map.put("angle", angle);
        map.put("depth", depth);
        map.put("distance_to_coast", distanceToCoast);
        map.put("company_id", id);
        map.put("distance_on_water", distanceOnWater);
        Number id = insert.executeAndReturnKey(map);
        return new Outlet((long) id, diameter, flowRate, waste, angle, depth, distanceToCoast, distanceOnWater);
    }

    public Outlet findOutlet(long id) {
        return outlets.find(id);
    }

    public Set<Outlet> findOutlets() {
        List<Outlet> query = template.query("SELECT * FROM outlets WHERE company_id = ?", new Object[]{id},
                (rs, rowNum) -> outlets.mapRow(rs, rowNum));
        return new HashSet<>(query);
    }
}
