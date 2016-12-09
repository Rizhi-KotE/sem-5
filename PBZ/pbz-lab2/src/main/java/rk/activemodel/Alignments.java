package rk.activemodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Alignments {

    @Autowired
    private JdbcTemplate template;

    public Alignment createAlignment(Outlet outlet, double distanceOnWater) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .withTableName("alignments")
                .usingGeneratedKeyColumns("alignment_id");
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("outlet_id", outlet.getId())
                .addValue("alignment_distance", distanceOnWater);
        Number alignment_id = insert.executeAndReturnKey(params);
        return new Alignment((long)alignment_id, distanceOnWater);
    }

    public Alignment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Alignment(rs.getLong("alignment_id"), rs.getDouble("alignment_distance"));
    }

    public Alignment find(long id) {
        return template.queryForObject("SELECT * FROM alignments WHERE alignment_id = ?", new Object[]{id}, this::mapRow);
    }
}
