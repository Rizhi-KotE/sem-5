package rk.activemodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class Outlets {

    @Autowired
    private JdbcTemplate template;

    public Outlet createNewOutlet(double diameter, double flowRate, double waste, double angle, double depth, double distanceToCoast) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .withTableName("outlets")
                .usingGeneratedKeyColumns("id");
        HashMap map = new HashMap();
        map.put("diameter", diameter);
        map.put("flow_rate", flowRate);
        map.put("waste", waste);
        map.put("angle", angle);
        map.put("depth", depth);
        map.put("distance_to_coast", distanceToCoast);
        Number id = insert.executeAndReturnKey(map);
        return new Outlet((long) id, diameter, flowRate, waste, angle, depth, distanceToCoast);
    }

    public Set<Outlet> find(){
        List<Outlet> outlets = template.query("SELECT * FROM outlets", outletRowMapper);
        return new HashSet<>(outlets);
    }

    public Outlet find(long idForFind) {

        return template.queryForObject("SELECT * FROM outlets WHERE id = ?", new Object[]{idForFind}, outletRowMapper);
    }

    public Outlet mapRow(ResultSet rs, int rowNum) throws SQLException {
      return outletRowMapper.mapRow(rs, rowNum);
    }

    public static RowMapper<Outlet> outletRowMapper = (rs, rowNum) -> {
        long id = rs.getLong("id");
        double diameter = rs.getDouble("diameter");
        double flowRate = rs.getDouble("flow_rate");
        double waste = rs.getDouble("waste");
        double angle = rs.getDouble("angle");
        double depth = rs.getDouble("depth");
        double distanceToCoast = rs.getDouble("distance_to_coast");
        return new Outlet(id, diameter, flowRate, waste, angle, depth, distanceToCoast);
    };
}
