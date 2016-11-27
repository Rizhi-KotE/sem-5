package rk.activemodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class IndividualMPDs {

    @Value("#{FIND_ALL_INDIVIDUAL_MPD}")
    public static String SELECT_FROM_INDIVIDUAL_MPD = "SELECT * FROM individual_mpd as mpd, " +
            "outlets as o" +
            " where mpd.outlet_id=o.outlet_id";

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private Outlets outlets;

    @Autowired
    private Alignments alignments;

    @Autowired
    private Substances substances;

    RowMapper<IndividualMPD> mpdRowMapper = (rs, rowNum) -> {
        Outlet outlet = outlets.mapRow(rs, rowNum);
        Substance substance = substances.mapRow(rs, rowNum);
        Alignment alignment = alignments.mapRow(rs, rowNum);
        double backgroundConcentration = rs.getDouble("background_concentration");
        double concentrationInEffluent = rs.getDouble("concentration_in_effluent");
        long id = rs.getLong("individual_mpd_id");
        return new IndividualMPD((long) id, outlet, substance, alignment, backgroundConcentration, concentrationInEffluent);
    };

    public IndividualMPD createMPD(Outlet outlet,
                                   Substance substance,
                                   Alignment alignment,
                                   double backgroundConcentration,
                                   double concentrationInEffluent) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .withTableName("individual_mpd")
                .usingGeneratedKeyColumns("individual_mpd_id");
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("outlet_id", outlet.getId())
                .addValue("alignment_id", alignment.getId())
                .addValue("substance_id", substance.getId())
                .addValue("background_concentration", backgroundConcentration)
                .addValue("concentration_in_effluent", concentrationInEffluent);
        Number id = insert.executeAndReturnKey(parameters);
         return new IndividualMPD((long)id, outlet, substance, alignment, backgroundConcentration,
                concentrationInEffluent);
    }

    public Set<IndividualMPD> find(Outlet outlet) {
        List<IndividualMPD> query = template.query(SELECT_FROM_INDIVIDUAL_MPD, mpdRowMapper);
        return new HashSet(query);
    }
}
