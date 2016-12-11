package rk.activemodel;

import java.util.Arrays;
import java.util.List;

public enum WaterUsageType {

    SingleUsage("обособленное водопользование", "single"),
    MultipleUsage("совместное водопользование", "multiple");

    private String name;
    private String bdType;

    WaterUsageType(String name, String bdType) {
        this.name = name;
        this.bdType = bdType;
    }

    public String getName() {
        return name;
    }

    public String getBdType() {
        return bdType;
    }

    public static List<WaterUsageType> getTypes(){
        return Arrays.asList(SingleUsage, MultipleUsage);
    }
}
