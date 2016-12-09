package rk.activemodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Substances {

    @Autowired
    private JdbcTemplate template;

    public Substance mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Substance(rs.getLong("substance_id"), rs.getString("substance_name"));
    }

    public Substance createSubstance(String substanceName) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .withTableName("substances")
                .usingGeneratedKeyColumns("substance_id");
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("substance_name", substanceName);
        long number = (long)insert.executeAndReturnKey(params);
        return new Substance(number, substanceName);
    }

    public Substance findSubstance(long substanceId) {
        return template.queryForObject("SELECT * FROM substances WHERE substance_id = ?", new Object[]{substanceId}, this::mapRow);
    }

    public Set<Substance> find() {
        List<Substance> query = template.query("SELECT * FROM substances", new Object[]{}, this::mapRow);
        return new HashSet<>(query);
    }
}
