package model.dao.implementation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.entities.Department;
import model.entities.Seller;

public class DaoImplService {
    protected static Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBirthDate(rs.getDate("BirthDate").toLocalDate());
        seller.setBaseSalary(rs.getBigDecimal("BaseSalary"));
        seller.setDepartment(department);
        return seller;
    }

    protected static Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("Department"));
        return department;
    }

    //Método apenas para teste. Não é uma boa prática utilizar, pode quebrar o sistema.
    protected static void reorganizeTable(Statement st) throws SQLException {
        String query = "SET @count = 0";
        st.executeUpdate(query);

        query = "UPDATE `seller` SET `seller`.`id` = @count:= @count + 1";
        st.executeUpdate(query);

        query = "SET GLOBAL auto_increment_increment = 1";
        st.executeUpdate(query);

        query = "SET GLOBAL auto_increment_offset = 1";
        st.executeUpdate(query);

        query = "ALTER TABLE `seller` AUTO_INCREMENT = 1";
        st.executeUpdate(query);
    }
}