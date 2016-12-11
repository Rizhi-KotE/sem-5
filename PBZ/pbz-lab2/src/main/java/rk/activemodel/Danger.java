package rk.activemodel;

import java.sql.ResultSet;
import java.sql.SQLException;

public enum Danger {
    NotDanger, Danger;

    public static Danger mapRow(ResultSet rs, int RowNum) throws SQLException {
        String type = rs.getString("danger_class");
        switch (type){
            case "danger": return Danger;
            case "not_danger": return NotDanger;
            default: throw new IllegalArgumentException("неверное значение");
        }
    }
}
