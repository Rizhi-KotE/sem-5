package rk.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.sql.Time;

@Entity
@Data
public class Measurement {
    @Id
    long id;

    @OneToOne
    private Outlet outlet;

    @OneToOne
    private Substance substance;

    private double PDK;

    private Date date;
}
