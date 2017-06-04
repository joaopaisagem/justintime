package pt.ipca.justintime.utils;


import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Vacation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Service
public class VacationUtils {

    static int maxDaysVacations = 22;

    /**
     * This method checks the number of days between dates
     * Returns the number of days
     * The arguments must specify a period of dates startDate and endDate
     *
     * @param startDate the starting date of vacation period
     * @param endDate   the end date of vacation period
     * @return a Long with the number of days
     */
    private long dateDiffInNumberOfDays(LocalDate startDate, LocalDate endDate) {

        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * This method checks if a date is difrent from Saturday = 6 and Sunday=7.
     * Returns TRUE or FALSE
     * Teh argument must specify a date
     *
     * @param date a "LocalDate" type
     * @return TRUE, FALSE
     */
    private boolean dateDiffOfWeekend(LocalDate date) {
        if (date.getDayOfWeek().getValue() != 6 && date.getDayOfWeek().getValue() != 7 && date.getYear() == LocalDate.now().getYear()) {
            return true;
        }
        return false;
    }


    /**
     * This method will check if the employee can add more vacations to is list
     * Returns numberOfDays if the value still in the range and he can add more vacations
     * Returns -1 if the employee exceed the maxDaysVacations
     * The argument must specify a vacation List and a Vacation from the input
     *
     * @param employeeList employee list
     * @param formVacation vacations from user input
     * @return numberOfDays, -1
     */
    public int numberOfAvailableDays(List<Vacation> employeeList, Vacation formVacation) {

        int numberOfDays = 0;

        List<Vacation> formVacationDays = new ArrayList<>();

        formVacationDays.add(formVacation);

        List<LocalDate> employeelistToCheck = getDaysBetweenDates(employeeList);

        List<LocalDate> formVacations = getDaysBetweenDates(formVacationDays);

        numberOfDays = maxDaysVacations - getTotalNumberOfWorkingDays(employeelistToCheck);

        numberOfDays = numberOfDays - getTotalNumberOfWorkingDays(formVacations);

        if (numberOfDays < 0) {

            return -1;

        } else {

            return numberOfDays;

        }
    }

    /**
     * Overload Method
     * This method will check if the employee can add more vacations to is list
     * Returns numberOfDays if the value still in the range and he can add more vacations
     * Returns -1 if the employee exceed the maxDaysVacations
     * The argument must specify a vacation List
     *
     * @param employeeVacationList
     * @return
     */
    public int numberOfAvailableDays(List<Vacation> employeeVacationList) {

        int numberOfDays = 0;


        List<LocalDate> employeelistToCheck = getDaysBetweenDates(employeeVacationList);

        numberOfDays = maxDaysVacations - getTotalNumberOfWorkingDays(employeelistToCheck);

        if (numberOfDays < 0) {

            return -1;

        } else {

            return numberOfDays;

        }
    }

    /**
     * This method is an Overload Method
     * Returns an  ArrayList of dates day by day.
     * The arguments must specify an Vacation List.
     * The name argument is a specifier that is relative to a list of dates.
     * This method always returns a dateList
     * When this method is called will create a dateList and will do enhanced loop through vacations
     * will get the number of days between the start date and end date
     * We run a for loop while the i is < then days, we create a LocalDate ,and we add to the list that day.
     *
     * @param vacationList list of vacations
     * @return a list of dates "dateList" returns a list with the days between the start and the end
     */
    public List<LocalDate> getDaysBetweenDates(List<Vacation> vacationList) {
        List<LocalDate> dateList = new ArrayList<>();
        for (Vacation vacation : vacationList) {
            long days = dateDiffInNumberOfDays(vacation.getVacationStartDay(), vacation.getVacationEndDay());
            for (int i = 0; i <= days; i++) {
                LocalDate d = vacation.getVacationStartDay().plus(i, ChronoUnit.DAYS);
                dateList.add(d);
            }
        }
        return dateList;
    }

    /**
     * Returns an  int with the number of days excluding Saturday = 6 and Sunday=7.
     * The arguments must specify an dateList of type LocalDate.
     * The name argument is a specifier that is relative to a list of dates.
     * This method always returns a counter with the number of days without weekends
     * We use a for each loop to check the date on the list and if the day is different from 6 or 7
     * the counter will increment by 1
     * returns the counter "count"
     *
     * @param dateList a list of "LocalDate"
     * @return the number of working days "count"
     */
    public int getTotalNumberOfWorkingDays(List<LocalDate> dateList) {
        int cont = 0;
        for (LocalDate date : dateList) {
            if (dateDiffOfWeekend(date)) {
                cont++;
            }
        }
        return cont;
    }

    /**
     * Returns a List of days of vacations but excluding Saturday = 6 and Sunday=7.
     * The arguments must specify a vacation list .
     * The name argument is a specifier that is relative to a list of vacations.
     * This method always returns a list of days
     * We use a for each loop to check the date on the list and if the day is different from 6 or 7
     * if the day is different from 6 or 7 we add the day to a list
     * returns a list of days
     *
     * @param vacationList a list of vacation
     * @return list of local dates.
     */
    public List<LocalDate> getWorkingDaysVacations(List<Vacation> vacationList) {
        List<LocalDate> listToReturn = new ArrayList<>();

        for (LocalDate date : getDaysBetweenDates(vacationList)) {
            if (dateDiffOfWeekend(date)) {
                listToReturn.add(date);
            }
        }
        return listToReturn;
    }

    /**
     * Returns a local date list with the days of vacations for the chosen month.
     * The arguments must specify an Vacation List .
     * Both argument names are specifier that is relative to a list of vacations and a month.
     * This method always returns a List with days in the chosen month
     * We use a for each loop to go through the dates received by "getDaysBetweenDates" method
     * if the day is in the current month we add the element to the list
     *
     * @param vacationList list of vacations
     * @param month        id of the month we want to get the days
     * @return list of local date for the chosen month
     */
    public List<LocalDate> getDaysOfVacationByMonth(List<Vacation> vacationList, int month) {
        List<LocalDate> listOfDaysToReturn = new ArrayList<>();

        for (LocalDate dayDate : getDaysBetweenDates(vacationList)) {
            if (dayDate.getMonth().getValue() == month) {
                listOfDaysToReturn.add(dayDate);
            }

        }
        return listOfDaysToReturn;
    }

    /**
     * This method tests if a a vacation already exists in the employee list
     * Returns TRUE or FALSE
     * The argument´s must specify a vacation and a list of vacations
     * Test´s if the list is null returns FALSE " means the list is empty"
     * if the list is different from null we compare the vacation with the vacation inside the list
     * if this vacation exists we return TRUE else we return FALSE
     *
     * @param vacation vacation
     * @param list     list of vacations
     * @return TRUE or FALSE
     */
    public boolean checkIfVacationsExist(Vacation vacation, List<Vacation> list) {
        if (list == null) {
            return false;
        }
        for (Vacation vac : list) {
            if (vac.equals(vacation))
                return true;
        }
        return false;
    }


    /**
     * This method is an Overloaded method used to test if a vacation is null or not
     * Returns TRUE or FALSE  .
     * The arguments must specify a Vacation "vacationSartDate and vacationEndDate".
     * Both argument names are specifier that is relative to a  vacations period.
     * This method always returns TRUE if the vacationStartDay is null
     * if the condition gives FALSE he will test if vacationEndDay is also null
     * if positive returns TRUE
     * of the conditions are both FALSE the method returns FALSE
     *
     * @param vacation
     * @return TRUE, FALSE
     */
    public boolean checkIfTheVacationIsNull(Vacation vacation) {
        if (vacation.getVacationStartDay() == null) {
            return true;
        } else if (vacation.getVacationEndDay() == null) {
            return true;
        }
        return false;
    }

    /**
     * This method is used to test if the vacations are in future and the user dosen´t insert past dates
     * Returns TRUE or FALSE .
     * The argument must be a Vacation period "vacationSartDate and vacationEndDate".
     * Verify if vacationSartDate is at least after the Localdate.now() "FUTURE"
     * Verify if the vacationEndDate is after the vacationSartDate so we have a period
     *
     * @param vacation
     * @return TRUE or FALSE
     */
    public boolean checkIfVacationsAreInFuture(Vacation vacation) {
        if (vacation.getVacationStartDay().isAfter(LocalDate.now())) {
            if (vacation.getVacationEndDay().isAfter(vacation.getVacationStartDay())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a proportion (n out of a total) as a percentage, in a float.
     */
        public float getPercentage(int n, int total) {
        float proportion = ((float) n) / ((float) total);
        return proportion * 100;
    }


}
