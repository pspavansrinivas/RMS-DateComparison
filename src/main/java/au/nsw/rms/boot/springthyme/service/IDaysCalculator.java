package au.nsw.rms.boot.springthyme.service;

import au.nsw.rms.boot.springthyme.exceptions.InvalidComparisonException;

/**
 * Created by pavan on 9/12/18.
 */
public interface IDaysCalculator<T> {

    int daysBetween(T startDate, T endDate) throws InvalidComparisonException;

}
