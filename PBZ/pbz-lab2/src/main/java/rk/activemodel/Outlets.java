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

    public Set<Outlet> find(){
        List<Outlet> outlets = template.query("SELECT * FROM outlets", outletRowMapper);
        return new HashSet<>(outlets);
    }

    public Outlet find(long idForFind) {

        return template.queryForObject("SELECT * FROM outlets WHERE outlet_id = ?", new
                Object[]{idForFind}, outletRowMapper);
    }

    public Outlet mapRow(ResultSet rs, int rowNum) throws SQLException {
      return outletRowMapper.mapRow(rs, rowNum);
    }

    public static RowMapper<Outlet> outletRowMapper = (rs, rowNum) -> {
        long id = rs.getLong("outlet_id");
        double diameter = rs.getDouble("diameter");
        double flowRate = rs.getDouble("flow_rate");
        double waste = rs.getDouble("waste");
        double angle = rs.getDouble("angle");
        double depth = rs.getDouble("depth");
        double distanceToCoast = rs.getDouble("distance_to_coast");
        double distanceOnWater = rs.getDouble("distance_on_water");
        return new Outlet(id, diameter, flowRate, waste, angle, depth, distanceToCoast, distanceOnWater);
    };

    public Set<Outlet> find(Company company) {
        List<Outlet> query = template.query("SELECT * FROM outlets WHERE company_id = ?", new
                Object[]{company.getId()}, outletRowMapper);
        return new HashSet<>(query);
    }
}
