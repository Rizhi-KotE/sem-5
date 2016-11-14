package rk.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class DangerRate {
    @Id
    private long id;
    @OneToOne
    private Substance substance;

    @OneToOne
    private Company company;

    private String dangerClass;

    private String group;
}
