package app;

import model.dao.DaoFactory;
import model.dao.GenericDao;
import model.entities.Department;

public class App2 {
    public static void main(String[] args) {
        GenericDao<Department> departmentDao = DaoFactory.createDepartmentDao();

        //Por simplicidade, os testes de null foram omitidos.
        System.out.println("---- TEST 1: Department findById method ----");
        System.out.println(departmentDao.findById(1));
        System.out.println();

        System.out.println("---- TEST 2: Department findAll method ----");
        for(Department department : departmentDao.findAll())
            System.out.println(department);
        System.out.println();

        System.out.println("---- TEST 3: Department insert method ----");
        Department department = new Department("Marketing");
        departmentDao.insert(department);
        System.out.println("Inserted! New department ID: " + department.getId());
        System.out.println();

        System.out.println("---- TEST 4: Seller update method ----");
        Department dpt = departmentDao.findById(5);
        dpt.setName("TESTING");
        departmentDao.update(dpt);
        System.out.println("Updated!");
        System.out.println();  

        System.out.println("---- TEST 5: Department deleteById method ----");
        departmentDao.deleteById(5);
        System.out.println();

        System.out.println("---- TEST 6: Department findByDepartment method ----");
        for(Department derpt : departmentDao.findByDepartment(2))
            System.out.println(derpt);
    }
}