package rk.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Stvor {
    @Id
    private long id;

    private int distance;
}
