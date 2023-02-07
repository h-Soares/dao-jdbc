package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import database.DBConfig;
import database.DBException;
import model.dao.GenericDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoImplJDBC implements GenericDao<Seller>{
    private Connection conn; 

    public SellerDaoImplJDBC(Connection conn) {
        this.conn = conn; //conexão de um determinado banco de dados QUALQUER.
    }

    @Override
    public void insert(Seller object) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(Seller object) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(Integer ID) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Seller findById(Integer ID) {
        String query = "SELECT seller.*, department.Name AS Department FROM seller JOIN department ON seller.DepartmentId = department.Id  WHERE seller.Id = ?";
        ResultSet rs = null;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("DepartmentId"));
                department.setName(rs.getString("Department"));

                Seller seller = new Seller();
                seller.setId(rs.getInt("Id"));
                seller.setName(rs.getString("Name"));
                seller.setEmail(rs.getString("Email"));
                seller.setBirthDate(rs.getDate("BirthDate").toLocalDate());
                seller.setBaseSalary(rs.getBigDecimal("BaseSalary"));
                seller.setDepartment(department);
                return seller;
            }
            return null;
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally {
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        // TODO Auto-generated method stub
        return null;
    }   
}