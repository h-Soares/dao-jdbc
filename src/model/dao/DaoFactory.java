package model.dao;

import database.DBConfig;
import model.dao.implementation.DepartmentDaoImplJDBC;
import model.dao.implementation.SellerDaoImplJDBC;
import model.entities.Department;
import model.entities.Seller;

public class DaoFactory {
    public static GenericDao<Seller> createSellerDao() { //Para não expor a implementação.
        return new SellerDaoImplJDBC(DBConfig.getConnection()); //uma implementação para Seller da interface genérica GenericDao. É UM...
    }

    public static GenericDao<Department> createDepartmentDao() {
        return new DepartmentDaoImplJDBC(DBConfig.getConnection());
    }
}