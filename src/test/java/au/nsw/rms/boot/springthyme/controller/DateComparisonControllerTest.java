package au.nsw.rms.boot.springthyme.controller;

import au.nsw.rms.boot.springthyme.exceptions.InvalidComparisonException;
import au.nsw.rms.boot.springthyme.service.IDaysCalculator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by pavan on 9/12/18.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DateComparisonController.class)
public class DateComparisonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IDaysCalculator<LocalDate> daysCalculator;

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Before
    public void setUp() throws Exception {
//        daysCalculator = new DaysCalculatorServiceImpl();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void whenGetOnCompareDate_thenRedirectToIndex() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/compareDate"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/index"));

    }

    @Test
    public void whenGetOnIndex_thenReturnHtml() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasNoErrors());

    }

    @Test
    public void whenPostOnCompareDate_thenCalculate() throws Exception {
        given(daysCalculator.daysBetween(any(), any())).willReturn(2);
        mvc.perform(MockMvcRequestBuilders.post("/compareDate").
                accept(MediaType.ALL).
                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                characterEncoding("UTF-8").
                content("startDate=13/12/2018&endDate=15/12/2018"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("daysBetween", 2));

    }

    @Test
    public void whenPostOnCompareEndDateIsEarlier_thenShowErrorMessage() throws Exception {
        given(daysCalculator.daysBetween(any(), any())).willThrow(InvalidComparisonException.class);

        mvc.perform(MockMvcRequestBuilders.post("/compareDate").
                accept(MediaType.ALL).
                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                characterEncoding("UTF-8").
                content("startDate=13/12/2018&endDate=12/12/2018"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("invalidComparison", true));

    }

}