package com.seek.food.employee.Service;

import com.seek.food.dto.Employee.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    public void insertEmployee(String employeeName);
    public List<EmployeeDTO> getSimpleEmployee(int start,int need);
    public List<EmployeeDTO> getSimpleByResign(boolean resign,int start,int need);
    public List<EmployeeDTO> searchSimpleByName(String employeeName,int start,int need);
    public List<EmployeeDTO> searchSimpleByDep(String depName,int start,int need);
    public EmployeeDTO getDetailEmployee(long employeeId);
    public void updateEmployee(EmployeeDTO employeeDTO);
    public void resignEmployee(long employeeId);
    public void deleteEmployee(long employeeId);
}
