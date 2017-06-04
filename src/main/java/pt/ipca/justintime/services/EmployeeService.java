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

    //////////////////////////////////////////////////////////
    //               CRUD METHOD`S                         //
    ////////////////////////////////////////////////////////
    public Employee saveEmployee(Employee employee) {

        addressRepository.save(employee.getAddressOne());
        addressRepository.save(employee.getAddressTwo());
        contactRepository.save(employee.getContactList());
        workSkillRepository.save(employee.getSkillList());
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {

        return employeeRepository.findOne(id);
    }

    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();

    }

    public long getNumberOfTotalEmployees() {

        return employeeRepository.count();
    }

    public void deleteEmployee(EmployeeForm employeeForm) {

        Employee employee = employeeFactory.transformEmployeeFormIntoEmployee(employeeForm);
        employeeRepository.delete(employee);
    }

    public void updateEmployee(Employee employee) {

        addressRepository.save(employee.getAddressOne());
        addressRepository.save(employee.getAddressTwo());
        contactRepository.save(employee.getContactList());
        workSkillRepository.save(employee.getSkillList());
        employeeRepository.saveAndFlush(employee);
    }

    //////////////////////////////////////////////////////////
    //         Employee vacation method´s                  //
    ////////////////////////////////////////////////////////

    /**
     * This Method gives the number of available days the employee have of vacations
     *
     * @param employeeList employee list
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
     * This Method gives the number of spent days the employee have from is vacations
     *
     * @param employeeList employee list
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
     * Method to get workingDaysVacations for one employee
     *
     * @param vacationList employee list
     * @return List<LocalDate> with working days only
     */
    private List<LocalDate> getTotalEmployeeVacation(List<Vacation> vacationList) {
        List<LocalDate> workingDaysVacations = vacationUtils.getWorkingDaysVacations(vacationList);
        return workingDaysVacations;
    }


    public Employee saveEmployeeVacations(Vacation vacation, Employee emp) {
        Employee employee = getEmployeeById(emp.getId());
        List<Vacation> vacationList = employee.getVacationList();
        vacationList.add(vacation);
        employee.setVacationList(vacationList);
        vacationRepository.save(vacation);
        employeeRepository.saveAndFlush(employee);
        return employee;
    }

    public boolean checkIfEmployeeExists(EmployeeForm employeeForm) {
        Employee employee = employeeFactory.transformEmployeeFormIntoEmployee(employeeForm);
        List<Employee> employeeList = getAllEmployees();
        for (Employee emp : employeeList)
            if (employee.equals(emp)) {
                return true;
            }
        return false;
    }


    public boolean saveEmployeeForm(EmployeeForm employeeForm) {
        if (checkIfEmployeeExists(employeeForm)) {
            return false;
        }
        Employee employee = employeeFactory.transformEmployeeFormIntoEmployee(employeeForm);
        saveEmployee(employee);
        return true;
    }


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


}
