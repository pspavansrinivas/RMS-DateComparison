package au.nsw.rms.boot.springthyme.controller;

import au.nsw.rms.boot.springthyme.exceptions.InvalidComparisonException;
import au.nsw.rms.boot.springthyme.form.DateForm;
import au.nsw.rms.boot.springthyme.service.IDaysCalculator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by pavan on 9/12/18.
 */
@Controller
public class DateComparisonController {

    private Logger logger = Logger.getLogger(DateComparisonController.class.getName());

    @Autowired
    @Setter
    private IDaysCalculator<LocalDate> daysCalculatorService;

    @Value("${application.frontend.datepicker.dateFormat}")
    private String datepickerDateFormat;

    @Value("${application.frontend.dateFormat.placeholder}")
    private String placeholderDateFormat;

    @Value("${application.frontend.dateFormat.conversion}")
    private String dateFormatConversion;


    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String getDateComparison(Model dataModel, @ModelAttribute("dateForm") DateForm dateForm) {
        if (dateForm != null)
            dateForm = new DateForm();

        dataModel.addAttribute("dateForm", dateForm);
        return "index";
    }

    @RequestMapping(value = {"/compareDate"}, method = RequestMethod.GET)
    public String redirectToIndex() {
        return "redirect:/index";
    }

    @RequestMapping(value = {"/compareDate"}, method = RequestMethod.POST)
    public String compareDate(Model model, @ModelAttribute("dateForm") DateForm dateForm, BindingResult bindingResult) throws InvalidComparisonException {
        int days = -1;
        if (Optional.ofNullable(dateForm.getStartDate()).isPresent() && Optional.ofNullable(dateForm.getEndDate()).isPresent()) {
            try {
                days = daysCalculatorService.daysBetween(dateForm.getStartDate(), dateForm.getEndDate());
                logger.info("Days between is " + days);
            } catch (InvalidComparisonException e) {
                logger.log(Level.SEVERE, "Invalid comparison", e);
                model.addAttribute("invalidComparison", true);
            }
            model.addAttribute("daysBetween", days);
        } else {
            model.addAttribute("errorMessage", "Invalid Start date or End date format");
        }
        model.addAttribute("dateForm", dateForm);

        return "index";
    }

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("datepickerDateFormat", datepickerDateFormat);
        model.addAttribute("placeholderDateFormat", placeholderDateFormat);
        model.addAttribute("dateFormatConversion", dateFormatConversion);
    }
}
