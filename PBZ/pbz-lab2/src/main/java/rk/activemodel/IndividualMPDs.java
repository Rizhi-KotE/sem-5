package rk.activemodel;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import rk.dto.DateRange;
import rk.dto.MpdDto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class IndividualMPDs {

    @Value("#{FIND_ALL_INDIVIDUAL_MPD}")
    public static String SELECT_FROM_INDIVIDUAL_MPD = "SELECT * FROM mpds_view";

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private Outlets outlets;

    @Autowired
    private Alignments alignments;

    @Autowired
    private Substances substances;

    public MeasurementIndividualMPD mapRow(ResultSet rs, int rowNum) throws SQLException {
        Outlet outlet = outlets.mapRow(rs, rowNum);
        Substance substance = substances.mapRow(rs, rowNum);
        Alignment alignment = alignments.mapRow(rs, rowNum);
        double backgroundConcentration = rs.getDouble("background_concentration");
        double concentrationInEffluent = rs.getDouble("concentration_in_effluent");
        double waste = rs.getDouble("waste");
        Date date = rs.getDate("date");
        long id = rs.getLong("individual_mpd_id");
        return new MeasurementIndividualMPD(id, outlet, substance, alignment, backgroundConcentration, concentrationInEffluent,
                waste, date);

    }

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
        return new MeasurementIndividualMPD((long) id, outlet, substance, alignment, backgroundConcentration,
                concentrationInEffluent, waste, date);
    }

    public Set<MeasurementIndividualMPD> find(Outlet outlet) {
        List<MeasurementIndividualMPD> query = template.query(SELECT_FROM_INDIVIDUAL_MPD, this::mapRow);
        return new HashSet(query);
    }

    public Set<MeasurementIndividualMPD> find(Company company) {
        List<MeasurementIndividualMPD> query = template
                .query("SELECT * FROM mpds_view WHERE company_id = ?", new Object[]{company.getId()}, this::mapRow);
        return new HashSet(query);
    }

    public Set<MeasurementIndividualMPD> find() {
        List<MeasurementIndividualMPD> query = template
                .query("SELECT * FROM mpds_view", this::mapRow);
        return new HashSet(query);
    }

    public Set<MeasurementIndividualMPD> find(Date dateBegin, Date dateEnd) {
        List<MeasurementIndividualMPD> query = template
                .query("SELECT * FROM mpds_view WHERE date BETWEEN ? AND ?", new Object[]{dateBegin, dateEnd}, this::mapRow);
        TreeSet<MeasurementIndividualMPD> mpDs = new TreeSet<>((o1, o2) -> Long.compare(o1.getId(), o2.getId()));
        mpDs.addAll(query);
        return mpDs;
    }

    public Map<Danger, List<Pair<Danger, MeasurementIndividualMPD>>> findGroupedByDanger(Company company) {
        List<Pair<Danger, MeasurementIndividualMPD>> query = template
                .query("SELECT DISTINCT * FROM mpds_view JOIN substance_danger_class USING (water_usage_type, substance_id) " +
                        "WHERE company_id = ? GROUP BY individual_mpd_id", new
                        Object[]{company.getId()}, (rs, rowNum) -> {
                    MeasurementIndividualMPD measurementIndividualMPD = mapRow(rs, rowNum);
                    Danger danger = Danger.mapRow(rs, rowNum);
                    return new Pair<Danger, MeasurementIndividualMPD>(danger, measurementIndividualMPD);
                });
        return query.stream()
                .collect(Collectors.groupingBy(o -> o.getKey()));
    }

    public MeasurementIndividualMPD find(long id) {
        return template.queryForObject("SELECT * FROM mpds_view WHERE individual_mpd_id = ?", new Object[]{id}, this::mapRow);
    }

    public Map<Outlet, List<MeasurementIndividualMPD>> getSubstancesGroupedByOutlet() {
        Set<MeasurementIndividualMPD> mpds = find();
        return mpds.stream().collect(Collectors.groupingBy(MeasurementIndividualMPD::getOutlet, Collectors.toList()));
    }

    public Map<Alignment, Map<Outlet, List<MeasurementIndividualMPD>>> getMPDGroupedByAlignmentAndThenOutlet() {
        Set<MeasurementIndividualMPD> mpds = find();
        return mpds
                .stream()
                .collect(Collectors.groupingBy(MeasurementIndividualMPD::getAlignment,
                        Collectors.groupingBy(MeasurementIndividualMPD::getOutlet,
                                Collectors.toList())));
    }


    public Set<MeasurementIndividualMPD> findToCompany(long id, DateRange dateBegin) {
        List<MeasurementIndividualMPD> query = template.query("SELECT * FROM mpds_view WHERE date BETWEEN ? AND ? AND company_id = ?",
                new Object[]{dateBegin.dateBegin, dateBegin.dateEnd, id}, this::mapRow);
        return new HashSet<>(query);
    }
}
