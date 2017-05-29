package pt.ipca.justintime.factorys;

import org.springframework.stereotype.Component;
import pt.ipca.justintime.domain.Vacation;
import pt.ipca.justintime.forms.VacationForm;

/**
 * Created by tiagosilva on 29/05/17.
 */
@Component
public class VacationFactory {

    public VacationForm transformVacationIntoVacationForm(Vacation vacation)
    {
        VacationForm vacationForm = new VacationForm();
        vacationForm.setId(vacation.getId());
        vacationForm.setVacationStartDay(vacation.getVacationStartDay());
        vacationForm.setVacationEndDay(vacation.getVacationEndDay());

        return vacationForm;
    }

    public Vacation transformVacationFormIntoVacation(VacationForm vacationForm)
    {
        Vacation vacation = new Vacation();

        vacation.setId(vacationForm.getId());
        vacation.setVacationStartDay(vacationForm.getVacationStartDay());
        vacation.setVacationEndDay(vacationForm.getVacationEndDay());

        return  vacation;
    }



}
