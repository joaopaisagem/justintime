package pt.ipca.justintime.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ipca.justintime.domain.Contact;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.domain.Vacation;
import pt.ipca.justintime.factorys.EmployeeFactory;
import pt.ipca.justintime.forms.EmployeeForm;
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

    public long getNumberOfTotalEmployees(){
        return employeeRepository.count();
    }
    public void updateEmployee(Employee employee) {
        addressRepository.save(employee.getAddressOne());
        addressRepository.save(employee.getAddressTwo());
        contactRepository.save(employee.getContactList());
        workSkillRepository.save(employee.getSkillList());
       // vacationRepository.save(employee.getVacationList());
        employeeRepository.saveAndFlush(employee);
    }

    //////////////////////////////////////////////////////////
    //         Employee vacation method´s                  //
    ////////////////////////////////////////////////////////

public List<Integer> getAllAvailableDaysVacations(List<Employee> employeeList) {
       List<Integer> list = new ArrayList<>();
    return list;
}
public List<Integer> getAllUnavailableDaysVacations(List<Employee> employeeList) {
    List<Integer> list = new ArrayList<>();
    return list;
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

    public boolean checkIfEmployeeExists(Employee employee){
        List<Employee> employeeList = getAllEmployees();
        for(Employee emp : employeeList)
        if(employee.equals(emp))
        {
            return true;
        }
        return false;
    }

    public boolean saveEmployeeForm (EmployeeForm employeeForm)
    {
        Employee employee = employeeFactory.transformEmployeeFormIntoEmployee(employeeForm);
         if(checkIfEmployeeExists(employee))
         {
             return false;
         }
         saveEmployee(employee);
         return true;
    }

}
