package pt.ipca.justintime.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.domain.Vacation;
import pt.ipca.justintime.factorys.EmployeeFactory;
import pt.ipca.justintime.factorys.VacationFactory;
import pt.ipca.justintime.forms.EmployeeForm;
import pt.ipca.justintime.forms.EmployeeVacationForm;
import pt.ipca.justintime.repositories.*;
import pt.ipca.justintime.utils.VacationUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Tiago Silva
 * @class LESI-PL 3º Ano
 * @number a7809
 * @email a7809@alunos.ipca.pt
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private VacationUtils vacationUtils;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private WorkSkillRepository workSkillRepository;
    @Autowired
    private VacationRepository vacationRepository;
    @Autowired
    private EmployeeFactory employeeFactory;

    /**
     * This method receive one argument
     * The argument must be one Employee
     * Saves the employee into database
     *
     * @param employee to save
     * @return saved employee
     */
    public Employee saveEmployee(Employee employee) {

        addressRepository.save(employee.getAddressOne());
        addressRepository.save(employee.getAddressTwo());
        contactRepository.save(employee.getContactList());
        workSkillRepository.save(employee.getSkillList());
        return employeeRepository.save(employee);
    }

    /**
     * This method receive one argument
     * The argument must be one Long id
     * the id must be from one employee
     * gets the employee by id
     *
     * @param id employee id
     * @return employee
     */
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findOne(id);
    }

    /**
     * This method dosent receive any argument
     * gets all employees from database
     *
     * @return List<Employee>
     */
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    /**
     * This method dosent receive any argument
     * Count the number of employees in database
     *
     * @return long number of employees
     */
    public long getNumberOfTotalEmployees() {

        return employeeRepository.count();
    }

    /**
     * This method receive one argument
     * The argument must be one employeeForm
     * deletes the employee from database
     * dosent have a return type
     *
     * @param employeeForm employee to delete
     */
    public void deleteEmployee(EmployeeForm employeeForm) {

        Employee employee = employeeFactory.transformEmployeeFormIntoEmployee(employeeForm);
        employeeRepository.delete(employee);
    }

    /**
     * This method receive one argument
     * The argument must be one employee
     * updates the employee from database
     * dosent have a return type
     *
     * @param employee to edit
     * @return edited employee
     */
    public Employee updateEmployee(Employee employee) {

        addressRepository.save(employee.getAddressOne());
        addressRepository.save(employee.getAddressTwo());
        contactRepository.save(employee.getContactList());
        workSkillRepository.save(employee.getSkillList());

        return employeeRepository.saveAndFlush(employee);

    }

    /**
     * This method receive one argument
     * The argument must be a List<Employee>
     * Calculate the number of available days the employees have of vacations
     *
     * @param employeeList
     * @return list of Integer
     */
    public List<Integer> getAllAvailableDaysVacations(List<Employee> employeeList) {

        List<Integer> list = new ArrayList<>();

        for (Employee employee : employeeList) {

            int counter = 0;
            List<LocalDate> vacationList = vacationUtils.getWorkingDaysVacations(employee.getVacationList());

            for (LocalDate date : vacationList) {

                if (date.isAfter(LocalDate.now())) {
                    counter++;
                }
            }
            list.add(counter);
        }

        return list;
    }

    /**
     * This method receive one argument
     * The argument must be a List<Employee>
     * Calculate the number of spent days the employee have from is vacations
     *
     * @param employeeList
     * @return list of Integer
     */
    public List<Integer> getAllUnavailableDaysVacations(List<Employee> employeeList) {

        List<Integer> list = new ArrayList<>();

        for (Employee employee : employeeList) {

            int counter = 0;

            List<LocalDate> vacationList = vacationUtils.getWorkingDaysVacations(employee.getVacationList());

            for (LocalDate date : vacationList) {

                if (date.isBefore(LocalDate.now())) {

                    counter++;
                }
            }
            list.add(counter);
        }

        return list;
    }

    /**
     * This method receive Two arguments
     * The arguments must be a (vacation , employee)
     * Saves the employee vacations into employee
     *
     * @param vacation vacation to save
     * @param emp employee to save
     * @return employee
     */
    public Employee saveEmployeeVacations(Vacation vacation, Employee emp) {

        Employee employee = getEmployeeById(emp.getId());
        List<Vacation> vacationList = employee.getVacationList();
        vacationList.add(vacation);
        employee.setVacationList(vacationList);
        vacationRepository.save(vacation);
        employeeRepository.saveAndFlush(employee);

        return employee;
    }

    /**
     * This method receive one arguments
     * The argument must be one employeeForm
     * checks if the employee exists
     *
     * @param employeeForm employee to check
     * @return TRUE , FALSE
     */
    private boolean checkIfEmployeeExists(EmployeeForm employeeForm) {

        Employee employee = employeeFactory.transformEmployeeFormIntoEmployee(employeeForm);
        List<Employee> employeeList = getAllEmployees();
        for (Employee emp : employeeList)
            if (employee.equals(emp)) {

                return true;
            }

        return false;
    }

    /**
     * This method receive one arguments
     * The argument must be one employeeForm
     * Saves one employeeform
     *
     * @param employeeForm employee to save
     * @return TRUE, FALSE
     */
    public boolean saveEmployeeForm(EmployeeForm employeeForm) {

        if (checkIfEmployeeExists(employeeForm)) {

            return false;
        }
        Employee employee = employeeFactory.transformEmployeeFormIntoEmployee(employeeForm);
        saveEmployee(employee);

        return true;
    }

    /**
     * This method receive one arguments
     * The argument must be one employeeVacationForm
     * Check if the vacation period exists on the employee
     *
     * @param employeeVacationForm employee and vacations
     * @return TRUE, FALSE
     */
    public boolean checkIfVacationExistOnTheEmployee(EmployeeVacationForm employeeVacationForm) {

        Employee employee = getEmployeeById(employeeVacationForm.getEmployee().getId());
        if (employee != null) {

            EmployeeForm employeeForm = employeeFactory.transformEmployeeIntoEmployeeForm(employee);
            for (Vacation vacation : employeeForm.getVacationList()) {

                if (vacation.getId() == employeeVacationForm.getVacation().getId()) ;
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * This method dosent receive any argument
     * Gives the Percentage of employees with vacation in the current month
     *
     * @return float
     */
    public float getPercentageOfEmployeesWithVacationInCurrentMonth()
    {
        List<Employee> employeeList = getAllEmployees();
        int numberOfEmployees = (int)getNumberOfTotalEmployees();
        int count= 0;
        for (Employee employee : employeeList){

         List<LocalDate> list = vacationUtils.getDaysOfVacationByMonth(employee.getVacationList(),LocalDate.now().getMonth().getValue());
         if(!list.isEmpty())
            {
                count++;
            }
        }

       return vacationUtils.getPercentage(count,numberOfEmployees);
    }

}
