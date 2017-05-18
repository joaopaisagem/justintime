package pt.ipca.justintime.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Contact;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.domain.Vacation;
import pt.ipca.justintime.repositories.*;
import pt.ipca.justintime.utils.VacationUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public void updateEmployee(Employee employee) {

        vacationRepository.save(employee.getVacationList());
        employeeRepository.saveAndFlush(employee);
    }

    //////////////////////////////////////////////////////////
    //         Employee vacation method´s                  //
    ////////////////////////////////////////////////////////
    public int getAllAvailableDaysVacations() {
        List<LocalDate> listOfVacations = getAllTotalEmployeeVacations();
        int contAvailable = 0;
        for (LocalDate date : listOfVacations) {
            if (date.isAfter(LocalDate.now())) {
                contAvailable = contAvailable + 1;
            }
        }
        return contAvailable;
    }

    public int getAllUnavailableDaysVacations() {
        List<LocalDate> listOfVacations = getAllTotalEmployeeVacations();
        int contUnavailable = 0;
        for (LocalDate date : listOfVacations) {
            if (date.isBefore(LocalDate.now())) {
                contUnavailable = contUnavailable + 1;
            }
        }
        return contUnavailable;
    }

    /**
     * Returns a list of vacation for the current year for each employee
     * This method Doesn't Receive an argument
     * Always returns an exception or a list with the vacation
     * We use a for each loop to get a list of all employees and iterate on each employee
     * For each employee we will call "getTotalEmployeeVacationsForCurrentYear"
     * that method receive one employee id and return a list of vacations
     *
     * @return the number of vacation  days or exception if there is no employees to search
     */

    public List<LocalDate> getAllTotalEmployeeVacations() {
        List<LocalDate> employeeVacationList = new ArrayList<>();
        if (employeeRepository.findAll() == null) {

        } else {
            for (Employee employee : employeeRepository.findAll()) {
                for (LocalDate date : getEmployeeVacationsForCurrentYear(employee.getId())) {
                    employeeVacationList.add(date);
                }
            }

        }
        return employeeVacationList;
    }

    /**
     * This method returns an int with total number of vacations for current year to one employee
     * This method receive one  argument that  must specify  one employee id
     * Always return  -1 if the employee doesn't exist or the number of vacations
     *
     * @param id must be relative to one employee
     * @return exeption or number of days
     */
    public List<LocalDate> getEmployeeVacationsForCurrentYear(Long id) {
        List<LocalDate> employeeVacationList = new ArrayList<>();
        if (employeeRepository.findOne(id) == null) {

        } else {

            Employee employee = employeeRepository.findOne(id);
            employeeVacationList = vacationUtils.getWorkingDaysVacations(employee.getVacationList());
        }

        return employeeVacationList;
    }

    public Employee saveEmployeeVacations(Vacation vacation , Employee employee){
        List<Vacation> vacationList = employee.getVacationList();
        vacationList.add(vacation);
        employee.setVacationList(vacationList);
        employeeRepository.saveAndFlush(employee);
        return employee;
    }


}
