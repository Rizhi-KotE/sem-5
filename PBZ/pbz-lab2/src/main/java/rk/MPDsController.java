package rk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import rk.activemodel.*;
import rk.dto.mpdDto;

import java.sql.Date;
import java.util.HashMap;

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
                .addObject("dto", new mpdDto())
                .addObject("companies", companies.find())
                .addObject("outlets", outlets.find())
                .addObject("substances", substances.find());
    }
}
