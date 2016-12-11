package rk.activemodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class Outlets {

    @Autowired
    private JdbcTemplate template;
    @Autowired
    private Companies companies;

    public Set<Outlet> find() {
        List<Outlet> outlets = template.query("SELECT * FROM outlets_view", this::mapRow);
        return new HashSet<>(outlets);
    }

    public Outlet find(long idForFind) {

        return template.queryForObject("SELECT * FROM outlets_view WHERE outlet_id = ?", new
                Object[]{idForFind}, this::mapRow);
    }

    public Outlet mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("outlet_id");
        double diameter = rs.getDouble("diameter");
        double flowRate = rs.getDouble("flow_rate");
        double waste = rs.getDouble("waste");
        double angle = rs.getDouble("angle");
        double depth = rs.getDouble("depth");
        double distanceToCoast = rs.getDouble("distance_to_coast");
        double distanceOnWater = rs.getDouble("distance_on_water");
        Company company = companies.mapRow(rs, rowNum);
        return new Outlet(company, id, diameter, flowRate, waste, angle, depth, distanceToCoast, distanceOnWater);
    }

    public Set<Outlet> find(Company company) {
        List<Outlet> query = template.query("SELECT * FROM outlets_view WHERE company_id = ?", new
                Object[]{company.getId()}, this::mapRow);
        return new HashSet<>(query);
    }

    public void remove(long outletId) {
        template.update("DELETE FROM outlets WHERE outlet_id = ?", new Object[]{outletId});
    }
}
