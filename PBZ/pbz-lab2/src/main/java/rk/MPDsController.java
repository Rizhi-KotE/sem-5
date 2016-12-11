package rk;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rk.activemodel.*;
import rk.dto.MpdDto;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@Controller
public class MPDsController {

    @Autowired
    private IndividualMPDs mpDs;

    @Autowired
    private Companies companies;

    @Autowired
    private Outlets outlets;

    @Autowired
    private Substances substances;

    @Autowired
    private Alignments alignments;

    @RequestMapping(value = "/mpd/create", method = RequestMethod.POST)
    private ModelAndView createMPD(@RequestBody HashMap<String, String> request) {

        Outlet outlet = outlets.find(Long.valueOf(request.get("outlet_id")));
        Substance substance = substances.findSubstance(Long.valueOf(request.get("substanceId")));
        Alignment alignment = alignments.find(Long.valueOf(request.get("alignment_id")));
        double backgroundConcentration = Double.valueOf(request.get("background_concentration"));
        double concentrationEffluent = Double.valueOf(request.get("concentration_effluent"));
        double waste = Double.valueOf(request.get("waste"));
        Date date = new Date(Long.valueOf(request.get("date")));
        mpDs.createMPD(outlet, substance, alignment, backgroundConcentration, concentrationEffluent, waste, date);
        return getMPDCreateForm();
    }

    @RequestMapping(value = "/mpd/create", method = RequestMethod.GET)
    public ModelAndView getMPDCreateForm() {
        return new ModelAndView("create_mpd")
                .addObject("dto", new MpdDto())
                .addObject("companies", companies.find())
                .addObject("outlets", outlets.find())
                .addObject("substances", substances.find());
    }

    @RequestMapping(value = "/mpd", params = {"companyId"})
    public ModelAndView getCompanyMpds(@RequestParam long companyId) {
        Company company = companies.findCompany(companyId);
        Set<MeasurementIndividualMPD> mpds = mpDs.find(company);
        return new ModelAndView("mpDsList")
                .addObject("mpDs", mpds);
    }

    @RequestMapping(value = "/mpd")
    public ModelAndView getMpds() {
        Set<MeasurementIndividualMPD> mpds = mpDs.find();
        return new ModelAndView("mpDsList")
                .addObject("mpDs", mpds);
    }

    @RequestMapping(value = "/mpd", params = {"dateBegin", "dateEnd"})
    public ModelAndView getMpdsByTime(Date dateBegin, Date dateEnd){
        Set<MeasurementIndividualMPD> mpds = mpDs.find(dateBegin, dateEnd);
        return new ModelAndView("mpDsList")
                .addObject("mpDs", mpds);
    }

    @RequestMapping(value = "/mpd/grouped", params = {"companyId"})
    public ModelAndView getMpdsGrouuped(@RequestParam long companyId){
        Company company = companies.findCompany(companyId);
        Map<Danger, List<Pair<Danger, MeasurementIndividualMPD>>> mpds = mpDs.findGroupedByDanger(company);
        return new ModelAndView("company-danger")
                .addObject("mpDs", mpds);
    }

    @RequestMapping(value = "/mpd/groupedByOutlet")
    public ModelAndView getMpdsGrouupedByOutlet(){
        Map<Outlet, List<MeasurementIndividualMPD>> substancesGroupedByOutlet = mpDs.getSubstancesGroupedByOutlet();
        return new ModelAndView("mpd_grouped_by_outlet")
                .addObject("map", substancesGroupedByOutlet);
    }

    @RequestMapping(value = "/mpd/groupedByAlignmentAndThenOutlet")
    public ModelAndView getMpdsGrouupedByAlignmentAndThenOutlet(){
        Map<Alignment, Map<Outlet, List<MeasurementIndividualMPD>>> mpdGroupedByAlignmentAndThenOutlet = mpDs.getMPDGroupedByAlignmentAndThenOutlet();
        return new ModelAndView("mpd_grouped_by_alignment_and_then_outlet")
                .addObject("map", mpdGroupedByAlignmentAndThenOutlet);
    }
}
