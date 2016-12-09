package rk.activemodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Companies {

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private Outlets outlets;
    private RowMapper<Company> companyRowMapper = (rs, rowNum) -> {
        long id = rs.getLong("company_id");
        String name = rs.getString("company_name");
        return getCompany(id, name);
    };

    public Company createCompany(String name) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(template)
                .withTableName("companies")
                .usingGeneratedKeyColumns("company_id");
        HashMap map = new HashMap();
        map.put("company_name", name);
        Number id = simpleJdbcInsert.executeAndReturnKey(map);
        return getCompany((long) id, name);
    }

    public Company findCompany(long i) {

        Company company = template
                .queryForObject("SELECT company_id, company_name FROM companies WHERE company_id = ?", new Object[]{i}, companyRowMapper);
        return company;
    }

    public Company getCompany(long id, String name) {
        Company company = new Company(id, name);
        company.setTemplate(template);
        company.setOutlets(outlets);
        return company;
    }

    public Set<Company> find() {
        List<Company> companies = template.query("SELECT * FROM companies", companyRowMapper);
        return new HashSet<>(companies);
    }

    public void remove(long id) {
        template.update("DELETE FROM companies WHERE company_id = ?", new Object[]{id});
    }

    public Company findCompany(Outlet outlet) {
        return template.queryForObject("SELECT * FROM companies WHERE companies.company_id IN (SELECT company_id FROM outlets " +
                "WHERE outlets.outlet_id = " +
                "?) ", new Object[]{outlet.getId()}, companyRowMapper);
    }
}
