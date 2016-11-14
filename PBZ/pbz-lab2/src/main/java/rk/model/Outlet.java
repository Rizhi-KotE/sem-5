package rk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Outlet {
    @Id
    private long id;

    private int diameter;

    private int sewageAngle;

    private int sewageDepth;

    private int distanceToShore;

    private int waterFlowRate;
}
