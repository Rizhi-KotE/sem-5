package rk.activemodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IndividualMPDs {

    @Value("#{FIND_ALL_INDIVIDUAL_MPD}")
    public static String SELECT_FROM_INDIVIDUAL_MPD = "SELECT * FROM individual_mpd as mpd, " +
            "outlets as o, substances as s, alignments as a" +
            " where mpd.outlet_id=o.outlet_id AND mpd.substance_id = s.substance_id AND " +
            "mpd.alignment_id = a.alignment_id";

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private Outlets outlets;

    @Autowired
    private Alignments alignments;

    @Autowired
    private Substances substances;

    RowMapper<MeasurementIndividualMPD> mpdRowMapper = (rs, rowNum) -> {
        Outlet outlet = outlets.mapRow(rs, rowNum);
        Substance substance = substances.mapRow(rs, rowNum);
        Alignment alignment = alignments.mapRow(rs, rowNum);
        double backgroundConcentration = rs.getDouble("background_concentration");
        double concentrationInEffluent = rs.getDouble("concentration_in_effluent");
        double waste = rs.getDouble("waste");
        Date date = rs.getDate("date");
        long id = rs.getLong("individual_mpd_id");
        return new MeasurementIndividualMPD((long) id, outlet, substance, alignment, backgroundConcentration, concentrationInEffluent, waste, date);
    };

    public MeasurementIndividualMPD createMPD(Outlet outlet,
                                              Substance substance,
                                              Alignment alignment,
                                              double backgroundConcentration,
                                              double concentrationInEffluent,
                                              double waste,
                                              Date date) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .withTableName("individual_mpd")
                .usingGeneratedKeyColumns("individual_mpd_id");
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("outlet_id", outlet.getId())
                .addValue("alignment_id", alignment.getId())
                .addValue("substance_id", substance.getId())
                .addValue("background_concentration", backgroundConcentration)
                .addValue("concentration_in_effluent", concentrationInEffluent)
                .addValue("date", date);
        Number id = insert.executeAndReturnKey(parameters);
         return new MeasurementIndividualMPD((long)id, outlet, substance, alignment, backgroundConcentration,
                concentrationInEffluent, waste, date);
    }

    public Set<MeasurementIndividualMPD> find(Outlet outlet) {
        List<MeasurementIndividualMPD> query = template.query(SELECT_FROM_INDIVIDUAL_MPD, mpdRowMapper);
        return new HashSet(query);
    }
}
