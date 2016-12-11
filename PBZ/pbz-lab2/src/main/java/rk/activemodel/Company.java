package rk.activemodel;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import rk.dto.CompanyDto;
import rk.dto.OutletDto;

import java.util.*;
import java.util.stream.Collectors;

;

public class Company {

    private final long id;
    private final String name;
    private JdbcTemplate template;
    private Outlets outlets;
    private WaterUsageType usageType;
    private IndividualMPDs mpds;

    Company(long id, String name, WaterUsageType usageType) {

        this.id = id;
        this.name = name;
        this.usageType = usageType;

    }

    public WaterUsageType getUsageType() {
        return usageType;
    }

    void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    void setOutlets(Outlets outlets) {
        this.outlets = outlets;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != company.id) return false;
        if (outlets != null ? !outlets.equals(company.outlets) : company.outlets != null) return false;
        return name != null ? name.equals(company.name) : company.name == null;

    }

    @Override
    public int hashCode() {
        int result = outlets != null ? outlets.hashCode() : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public Set<Outlet> findAllOutlets() {
        return outlets.find(this);
    }

    public Outlet createOutlet(double diameter,
                               double flowRate,
                               double waste,
                               double angle,
                               double depth,
                               double distanceToCoast,
                               double distanceOnWater) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template)
                .withTableName("outlets")
                .usingGeneratedKeyColumns("outlet_id");
        HashMap map = new HashMap();
        map.put("diameter", diameter);
        map.put("flow_rate", flowRate);
        map.put("waste", waste);
        map.put("angle", angle);
        map.put("depth", depth);
        map.put("distance_to_coast", distanceToCoast);
        map.put("company_id", id);
        map.put("distance_on_water", distanceOnWater);
        Number id = insert.executeAndReturnKey(map);
        return new Outlet(this, (long) id, diameter, flowRate, waste, angle, depth, distanceToCoast, distanceOnWater);
    }

    public Outlet findOutlet(long id) {
        return outlets.find(id);
    }

    public Set<Outlet> findOutlets() {
        List<Outlet> query = template.query("SELECT * FROM outlets_view WHERE company_id = ?", new Object[]{id},
                (rs, rowNum) -> outlets.mapRow(rs, rowNum));
        return new HashSet<>(query);
    }

    public void remove(){
        template.update("DELETE FROM companies WHERE company_id = ?", new Object[]{id});
    }

    public void update(CompanyDto dto){
        template.update("UPDATE companies SET company_name=?, water_usage_type=? WHERE company_id = ?",
                new Object[]{dto.companyName, dto.waterUsage, id});
    }

    public Outlet createOutlet(OutletDto request) {
        return createOutlet(request.diameter, request.flowRate, request.waste, request.angle, request.depth, request.distanceToCoast,
                request.distance);
    }

    public Map<Outlet, List<MeasurementIndividualMPD>> getSubstancesGroupedByOutlet(){
        Set<MeasurementIndividualMPD> mpds = this.mpds.find(this);
        return mpds.stream().collect(Collectors.groupingBy(MeasurementIndividualMPD::getOutlet, Collectors.toList()));
    }

    public void setMpds(IndividualMPDs mpds) {
        this.mpds = mpds;
    }

    public IndividualMPDs getMpds() {
        return mpds;
    }
}
