package pt.ipca.justintime.factorys;

import org.springframework.stereotype.Component;
import pt.ipca.justintime.domain.Employee;
import pt.ipca.justintime.forms.EmployeeForm;

/**
 * Created by Tiago Silva on 28/05/2017.
 */
@Component
public class EmployeeFactory {

    public Employee transformEmployeeFormIntoEmployee(EmployeeForm employeeForm) {
        Employee employee = new Employee();

        employee.setId(employeeForm.getId());
        employee.setFirstName(employeeForm.getFirstName());
        employee.setLastName(employeeForm.getLastName());
        employee.setEmail(employeeForm.getEmail());
        employee.setBirthdayDate(employeeForm.getBirthdayDate());
        employee.setNif(employeeForm.getNif());
        employee.setAddressOne(employeeForm.getAddressOne());
        employee.setAddressTwo(employeeForm.getAddressTwo());
        employee.setEmployeeTeamName(employeeForm.getEmployeeTeamName());
        employee.setGender(employeeForm.getGender());
        employee.setPicture(employeeForm.getPicture());
        employee.setSkillList(employeeForm.getSkillList());
        employee.setContactList(employeeForm.getContactList());
        employee.setVacationList(employeeForm.getVacationList());

        return employee;
    }

    public EmployeeForm transformEmployeeIntoEmployeeForm(Employee employee) {
        EmployeeForm employeeForm = new EmployeeForm();

        employeeForm.setId(employee.getId());
        employeeForm.setFirstName(employee.getFirstName());
        employeeForm.setLastName(employee.getLastName());
        employeeForm.setEmail(employee.getEmail());
        employeeForm.setBirthdayDate(employee.getBirthdayDate());
        employeeForm.setNif(employee.getNif());
        employeeForm.setAddressOne(employee.getAddressOne());
        employeeForm.setAddressTwo(employee.getAddressTwo());
        employeeForm.setEmployeeTeamName(employee.getEmployeeTeamName());
        employeeForm.setGender(employee.getGender());
        employeeForm.setPicture(employee.getPicture());
        employeeForm.setSkillList(employee.getSkillList());
        employeeForm.setContactList(employee.getContactList());
        employeeForm.setVacationList(employee.getVacationList());

        return employeeForm;
    }

}


