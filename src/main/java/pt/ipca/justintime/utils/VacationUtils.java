package pt.ipca.justintime.utils;


import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Vacation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class VacationUtils {

    /**
     * Returns an  ArrayList of dates day by day.
     * The arguments must specify an LocalDate.
     * The name argument is a specifier that is relative to a list of dates.
     * This method always returns a dateList
     * When this method is called will create a dateList and will check if the startDate is more recent then the endDate
     * Since we will count the end day we add to our list date = date.plusDays(1)
     * The for loop will incrementally add the day (data) to the list.
     *
     * @param startDate
     * @param endDate
     * @return a list of dates "dateList"
     */
    public List<LocalDate> getDaysBetweenDates(LocalDate startDate, LocalDate endDate) {
        long days = dateDiffInNumberOfDays(startDate, endDate);
        List<LocalDate> dateList = new ArrayList();

        for (int i = 0; i < days; i++) {
            LocalDate d = startDate.plus(i, ChronoUnit.DAYS);
            dateList.add(d);
        }
        return dateList;
    }

    private long dateDiffInNumberOfDays(LocalDate startDate, LocalDate endDate) {

        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * This method is an Overload Method
     * Returns an  ArrayList of dates day by day.
     * The arguments must specify an Vacation List.
     * The name argument is a specifier that is relative to a list of dates.
     * This method always returns a dateList
     * When this method is called will create a dateList and will do enhanced loop through vacations
     * will get the number of days between the start date and end date
     * We run a for loop while the i is < then days, we create a localdate ,and we add to the list that day.
     *
     * @param vacationList cenas
     * @return a list of dates "dateList" returns a list with the days between the start and the end
     */
    public List<LocalDate> getDaysBetweenDates(List<Vacation> vacationList) {
        List<LocalDate> dateList = new ArrayList<>();
        for (Vacation vacation : vacationList) {
            long days = dateDiffInNumberOfDays(vacation.getVacationStartDay(), vacation.getVacationEndDay());
            for (int i = 0; i < days; i++) {
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
            if (date.getDayOfWeek().getValue() != 6 && date.getDayOfWeek().getValue() != 7 && date.getYear() == LocalDate.now().getYear()) {
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
     * We use a for each loop to transform first  the vacation list into a List of days
     * We use a for each loop to check the date on the list and if the day is different from 6 or 7
     * if the day is difrent from 6 or 7 we add the day to a list
     * returns a list of days
     *
     * @param vacationList a list of vacation
     * @return list of local dates.
     */
    public List<LocalDate> getWorkingDaysVacations(List<Vacation> vacationList) {
        List<LocalDate> listToReturn = new ArrayList<>();
        for (Vacation vacation : vacationList) {
            for (LocalDate date : getDaysBetweenDates(vacation.getVacationStartDay(), vacation.getVacationEndDay())) {
                if (date.getDayOfWeek().getValue() != 6 && date.getDayOfWeek().getValue() != 7 && date.getYear() == LocalDate.now().getYear()) {
                    listToReturn.add(date);
                }
            }
        }
        return listToReturn;
    }

    /**
     * Returns a local date list with the days of vacations for the chosen month.
     * The arguments must specify an Vacation List .
     * Both argument names are specifier that is relative to a list of vacations and a month.
     * This method always returns a List with days in the chosen month
     * We use a for each loop to go through the vacation list then we pass the vacation start and end date to the Method
     * "getDaysBetweenDates" to get day by day and then we do another for each to check if the day is in the current month
     * if yes we add the day to the list of days to return.
     *
     * @param vacationList list of vacations
     * @param month        id of the month we want to get the days
     * @return list of local date for the chosen month
     */
    public List<LocalDate> getDaysOfVacationByMonth(List<Vacation> vacationList, int month) {
        List<LocalDate> listOfDaysToReturn = new ArrayList<>();

        for (Vacation date : vacationList) {
            for (LocalDate dayDate : getDaysBetweenDates(date.getVacationStartDay(), date.getVacationEndDay())) {
                if (dayDate.getMonth().getValue() == month) {
                    listOfDaysToReturn.add(dayDate);

                }
            }

        }
        return listOfDaysToReturn;
    }

    public boolean checkIfVacationsExist(Vacation vacation, List<Vacation> list)
    {
        if(list == null)
        {
            return false;
        }else{
            for (Vacation vac : list)
            {
                if (vac.getVacationStartDay()== vacation.getVacationStartDay()& vac.getVacationEndDay()== vacation.getVacationEndDay())
                    return true;
            }
            return false;
        }

    }
    public boolean getDatesForCurrentYear(LocalDate date) {

        if (date.getYear() == LocalDate.now().getYear()) {
            return true;
        }
        return false;
    }
}
