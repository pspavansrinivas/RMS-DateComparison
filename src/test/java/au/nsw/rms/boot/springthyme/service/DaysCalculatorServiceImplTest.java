package au.nsw.rms.boot.springthyme.service;

import au.nsw.rms.boot.springthyme.exceptions.InvalidComparisonException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by pavan on 9/12/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DaysCalculatorServiceImplTest {

    private IDaysCalculator<LocalDate> serviceUnderTest;
    private DateTimeFormatter format;


    @Before
    public void setup(){
        serviceUnderTest = new DaysCalculatorServiceImpl();
        format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    @Test
    public void whenSameDateIsGiven_thenReturnShouldBeZero() throws Exception {
        assertThat(serviceUnderTest.daysBetween(LocalDate.parse("15/12/2018", format), LocalDate.parse("15/12/2018", format))).isEqualTo(0);
    }

    @Test(expected = InvalidComparisonException.class)
    public void whenEndDateIsBeforeStartDate_thenReturnShouldThrowInvalidComparisonException() throws Exception {
        serviceUnderTest.daysBetween(LocalDate.parse("15/12/2018", format), LocalDate.parse("14/12/2018", format));
    }

    @Test
    public void whenProperDatesAreGiven_thenReturnShouldValidNumber() throws Exception {
        assertThat(serviceUnderTest.daysBetween(LocalDate.parse("15/12/2018", format), LocalDate.parse("17/12/2018", format))).isEqualTo(2);;
    }

}