package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
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
                Department department = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, department);
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

    private Department instantiateDepartment(ResultSet rs) throws SQLException { //talvez depois colocar na classe responsável.
        Department department = new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("Department"));
        return department;
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBirthDate(rs.getDate("BirthDate").toLocalDate());
        seller.setBaseSalary(rs.getBigDecimal("BaseSalary"));
        seller.setDepartment(department);
        return seller;
    }

    @Override
    public List<Seller> findAll() {
        // TODO Auto-generated method stub
        return null;
    }   

    @Override
    public List<Seller> findByDepartment(Integer departmentId) {
        List<Seller> sellerList = new LinkedList<>();
        String query = "SELECT seller.*, department.Name AS Department FROM seller JOIN department " + 
                       "ON seller.DepartmentId = department.Id WHERE seller.DepartmentId = ? ORDER BY seller.Name";
        ResultSet rs = null;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, departmentId);
            rs = pstmt.executeQuery();

            if(rs.isBeforeFirst()) {
                Department department = instantiateDepartmentById(departmentId);
                while(rs.next()) {
                    sellerList.add(instantiateSeller(rs, department));
                }
            }
            return sellerList;
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally {
            DBConfig.closeResultSet(rs);
        } 
    }
    private Department instantiateDepartmentById(Integer departmentId) {
        String query = "SELECT * FROM department WHERE Id = ?";
        ResultSet rs = null;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, departmentId);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("Id"));
                department.setName(rs.getString("Name"));
                return department;
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
}