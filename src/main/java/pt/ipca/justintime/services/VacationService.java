package pt.ipca.justintime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.domain.Vacation;
import pt.ipca.justintime.factorys.VacationFactory;
import pt.ipca.justintime.forms.EmployeeVacationForm;
import pt.ipca.justintime.repositories.EmployeeRepository;
import pt.ipca.justintime.repositories.VacationRepository;
import pt.ipca.justintime.utils.VacationUtils;

import java.util.List;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Service
public class VacationService {

    @Autowired
    private VacationRepository vacationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private VacationFactory vacationFactory;
    @Autowired
    private VacationUtils vacationUtils;

    /**
     * This method receive one argument
     * The argument must be a Long id
     * Get vacation with the id from database
     *
     * @param id vacation id "Long"
     * @return Vacation
     */
    public Vacation getVacationById(Long id) {

        return vacationRepository.findOne(id);
    }

    /**
     * This method receive one argument
     * The argument must be related to EmployeeVacationForm
     * Updates the employee vacations
     *
     * @param employeeVacationForm employee and vacation
     * @return TRUE,FALSE
     */
    public boolean updateEmployeeVacations(EmployeeVacationForm employeeVacationForm) {

        Employee employee = employeeRepository.getOne(employeeVacationForm.getEmployee().getId());
        Vacation vacation = vacationFactory.transformVacationFormIntoVacation(employeeVacationForm.getVacation());

        if(vacationUtils.checkIfVacationsExist(vacation, employee.getVacationList())){

            return false;
        }else if (vacationUtils.checkIfVacationsAreInFuture(vacation)) {

            vacationRepository.saveAndFlush(vacation);

            return true;
        }
        else

            return false;
    }

}
