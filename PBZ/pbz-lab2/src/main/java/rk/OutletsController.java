package rk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import rk.activemodel.Companies;
import rk.activemodel.Company;
import rk.activemodel.Outlet;
import rk.activemodel.Outlets;
import rk.dto.OutletDto;

import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class OutletsController {

    @Autowired
    private Companies companies;

    @Autowired
    private Outlets outlets;

    @RequestMapping(value = "/outlet", method = POST)
    public ModelAndView createOutlet(@ModelAttribute OutletDto request) {

        Company company = companies.findCompany(request.companyId);
        company.createOutlet(request);
        return new ModelAndView("redirect:/company/" + request.companyId);
    }

    @RequestMapping(value = "/outlet", method = PUT)
    public ModelAndView update(@ModelAttribute OutletDto request) {
        companies.updateOutlet(request);
        return new ModelAndView("redirect:/company/" + request.companyId);
    }

    @RequestMapping(value = "/company/{companyId}/outlet/create", method = GET)
    public ModelAndView createOutlet(@PathVariable long companyId) {
        OutletDto outletDto = new OutletDto();
        outletDto.companyId = companyId;
        return new ModelAndView("outlet_create")
                .addObject("dto", outletDto);
    }

    @RequestMapping(value = "/company/{companyId}/outlet/{outletId}/update", method = GET)
    public ModelAndView updateOutlet(@PathVariable long companyId, @PathVariable long outletId) {
        Company company = companies.findCompany(companyId);
        Outlet outlet = company.findOutlet(outletId);
        return new ModelAndView("outlet_update")
                .addObject("dto", new OutletDto(outlet));
    }

    @RequestMapping(value = "/company/{companyId}/outlet/{outletId}", method = DELETE)
    public ModelAndView deleteOutlet(@PathVariable long companyId, @PathVariable long outletId) {
        outlets.remove(outletId);
        return new ModelAndView("redirect:/company/" + companyId);
    }

    @RequestMapping(value = "/outlet/{outletId}", method = GET)
    public ModelAndView getOutlet(@PathVariable long outletId) {
        Outlet outlet = outlets.find(outletId);
        Company company = companies.findCompany(outlet);
        return new ModelAndView("outlet-info")
                .addObject("companyName", company)
                .addObject("outlet", outlet);
    }
}
