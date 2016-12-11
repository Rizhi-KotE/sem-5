package rk.activemodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import rk.dto.CompanyDto;
import rk.dto.OutletDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Companies {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private WaterUsages waterUsages;

    @Autowired
    private Outlets outlets;

    @Autowired
    private IndividualMPDs mpDs;

    public Company mapRow(ResultSet rs, int index) throws SQLException {
        long id = rs.getLong("company_id");
        String name = rs.getString("company_name");
        WaterUsageType waterUsageType = waterUsages.mapRow(rs, index);
        return getCompany(id, name, waterUsageType);
    }

    public Company createCompany(String name, WaterUsageType usageType) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(template)
                .withTableName("companies")
                .usingGeneratedKeyColumns("company_id");
        HashMap map = new HashMap();
        map.put("company_name", name);
        map.put("water_usage_type", usageType.getBdType());
        Number id = simpleJdbcInsert.executeAndReturnKey(map);
        return getCompany((long) id, name, usageType);
    }

    public Company findCompany(long i) {

        Company company = template
                .queryForObject("SELECT * FROM companies WHERE company_id = ?", new Object[]{i}, this::mapRow);
        return company;
    }

    private Company getCompany(long id, String name, WaterUsageType usage) {
        Company company = new Company(id, name, usage);
        company.setTemplate(template);
        company.setOutlets(outlets);
        company.setMpds(mpDs);
        return company;
    }

    public Set<Company> find() {
        List<Company> companies = template.query("SELECT * FROM companies", this::mapRow);
        return new HashSet<>(companies);
    }

    public void remove(long id) {
        template.update("DELETE FROM companies WHERE company_id = ?", new Object[]{id});
    }

    public Company findCompany(Outlet outlet) {
        return template.queryForObject("SELECT * FROM companies WHERE companies.company_id IN (SELECT company_id FROM outlets " +
                "WHERE outlets.outlet_id = " +
                "?) ", new Object[]{outlet.getId()}, this::mapRow);
    }

    public Company createCompany(CompanyDto companyDto) {
        return createCompany(companyDto.companyName, waterUsages.getUsage(companyDto.waterUsage));
    }

    public void updateOutlet(OutletDto request) {
        template.update("UPDATE outlets SET diameter = ?, flow_rate = ?," +
                " waste = ?, angle = ?," +
                "depth = ?, distance_to_coast = ?, distance_on_water = ?" +
                "WHERE outlet_id = ?", new Object[]{request.diameter,
                request.flowRate, request.waste, request.angle, request.depth, request.distanceToCoast,
                request.distance, request.outletId});
    }
}
