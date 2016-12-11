package rk.activemodel;

import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@Service
public class WaterUsages {

    Map<String, WaterUsageType> waterUsageTypeMap = new HashMap<>();

    {
        waterUsageTypeMap.put("single", WaterUsageType.SingleUsage);
        waterUsageTypeMap.put("multiple", WaterUsageType.MultipleUsage);
    }

    WaterUsageType mapRow(ResultSet rs, int rowNum) throws SQLException {
        String waterUsageType = rs.getString("water_usage_type");
        return waterUsageTypeMap.get(waterUsageType);
    }

    public WaterUsageType getUsage(String waterUsage) {
        return waterUsageTypeMap.get(waterUsage);
    }
}
