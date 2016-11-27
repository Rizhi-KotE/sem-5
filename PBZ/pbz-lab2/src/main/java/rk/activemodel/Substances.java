package rk.activemodel;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Substances {
    public Substance mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Substance(rs.getLong("substance_id"));
    }
}
