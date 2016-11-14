package rk.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Substance {
    @Id
    private long id;

    private String name;
}
