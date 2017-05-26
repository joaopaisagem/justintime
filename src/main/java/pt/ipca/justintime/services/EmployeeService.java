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
import java.util.Locale;

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

    public long getNumberOfTotalEmployees(){
        return employeeRepository.count();
    }
    public void updateEmployee(Employee employee) {

        vacationRepository.save(employee.getVacationList());
        employeeRepository.saveAndFlush(employee);
    }

    //////////////////////////////////////////////////////////
    //         Employee vacation method´s                  //
    ////////////////////////////////////////////////////////


    /**
     * This method return a list on integer with all employees available vacation dates
     * Dosen´t receive any argument
     *
     * @return List<Integer>
     */
    public List<Integer> getAllAvailableDaysVacations(List<Employee> employeeList) {
        List<Integer> totalAvailableVacation = new ArrayList<>();
        for (Employee employee: employeeList) {
            int count = 0;
            List<LocalDate> vacations = getTotalEmployeeVacation(employee.getVacationList());
            for(LocalDate date : vacations) {
               if (date.isAfter(LocalDate.now())) {
                   count++;
               }
           }
           if(count !=0) {
               totalAvailableVacation.add(count);
           }
        }
        return totalAvailableVacation;
    }
    /**
     * This method return a list on integer with all employees spent vacation dates
     * Dosen´t receive any argument
     *
     * @return List<Integer>
     */
    public List<Integer> getAllUnavailableDaysVacations(List<Employee> employeeList) {

        List<Integer> totalAvailableVacation= new ArrayList<>(employeeList.size());
        for (Employee employee: employeeList) {
            int count = 0;
            List<LocalDate> vacations = getTotalEmployeeVacation(employee.getVacationList());
            for(LocalDate date : vacations) {
                if (date.isBefore(LocalDate.now())) {
                    count++;
                }
            }
            if(count!=0) {
                totalAvailableVacation.add(count);
            }
        }
        return totalAvailableVacation;
    }

    /**
     * Method to get workingDaysVacations for one employee
     *
     * @param vacationList employee list
     * @return List<LocalDate> with working days only
     */
    private List<LocalDate> getTotalEmployeeVacation(List<Vacation> vacationList){
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


}
