package rk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rk.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
