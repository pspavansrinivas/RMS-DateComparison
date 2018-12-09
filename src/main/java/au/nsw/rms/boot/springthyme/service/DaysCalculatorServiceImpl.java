package au.nsw.rms.boot.springthyme.service;

import au.nsw.rms.boot.springthyme.exceptions.InvalidComparisonException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Created by pavan on 9/12/18.
 */
@Service
public class DaysCalculatorServiceImpl implements IDaysCalculator<LocalDate> {


    @Override
    public int daysBetween(LocalDate startDate, LocalDate endDate) throws InvalidComparisonException {

        if(endDate.isBefore(startDate)) {
            throw new InvalidComparisonException("Invalid date");
        }

        return startDate.until(endDate).getDays();
    }
}
