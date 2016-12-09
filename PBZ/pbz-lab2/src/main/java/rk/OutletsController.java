package rk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import rk.activemodel.Companies;
import rk.activemodel.Company;
import rk.activemodel.Outlet;
import rk.activemodel.Outlets;

import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class OutletsController {

    @Autowired
    private Companies companies;

    @Autowired
    private Outlets outlets;

    @RequestMapping(value = "/companyName/{companyId}/outlet", method = POST)
    public ModelAndView createOutlet(@PathVariable long companyId, @RequestBody HashMap<String, String > request){

        Company company = companies.findCompany(companyId);
        double diameter = Double.valueOf(request.get("diameter"));
        double flowRate = Double.valueOf(request.get("flowRate"));
        double waste = Double.valueOf(request.get("waste"));
        double angle = Double.valueOf(request.get("angle"));
        double depth = Double.valueOf(request.get("depth"));
        double distanceToCoast = Double.valueOf(request.get("distanceToCoast"));
        double distanceOnWater = Double.valueOf(request.get("distanceOnWater"));
        Outlet outlet = company.createOutlet(diameter, flowRate, waste, angle, depth, distanceToCoast, distanceOnWater);
        return new ModelAndView("outlet-info")
                .addObject("companyName", company)
                .addObject("outlet", outlet);
    }

    @RequestMapping(value = "/outlet/{outletId}", method = GET)
    public ModelAndView getOutlet(@PathVariable long outletId){
        Outlet outlet = outlets.find(outletId);
        Company company = companies.findCompany(outlet);
        return new ModelAndView("outlet-info")
                .addObject("companyName", company)
                .addObject("outlet", outlet);
    }
}
