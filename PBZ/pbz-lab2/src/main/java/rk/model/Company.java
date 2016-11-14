package rk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Company {
    @Id
    private long id;
    
    @Column(name = "name")
    private String name;

    @OneToMany
    private List<Outlet> outlets;

    @Column
    private String town;
}
