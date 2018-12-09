package au.nsw.rms.boot.springthyme.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Created by pavan on 9/12/18.
 */
@Configuration
public class ApplicationConfiguration {
    //To convert string to LocalDate
    @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class,null , new CustomDateEditor(new SimpleDateFormat(dateFormatConversion),true ));
    }

    @Value("${application.frontend.dateFormat.conversion}")
    private String dateFormatConversion;
}
