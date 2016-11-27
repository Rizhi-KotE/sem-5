package rk.activemodel;

import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class Alignments {
    public Alignment mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Alignment(rs.getLong("alignment_id"));
    }
}
