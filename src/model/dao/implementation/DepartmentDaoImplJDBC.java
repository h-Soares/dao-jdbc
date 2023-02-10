package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import database.DBConfig;
import database.DBException;
import database.DBIntegrityException;
import model.dao.GenericDao;
import model.entities.Department;

public class DepartmentDaoImplJDBC implements GenericDao<Department> {
    private Connection conn;

    public DepartmentDaoImplJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        String query = "INSERT INTO department VALUES (DEFAULT, ?)";
        ResultSet rs = null;

        try(PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            
            pstmt.setString(1, department.getName());
            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected > 0) {
                ResultSet rowsResult = pstmt.getGeneratedKeys();
                if(rowsResult.next()) {
                    department.setId(rowsResult.getInt(1));    
                }
                conn.commit();
                DBConfig.closeResultSet(rowsResult);
            }
            else {
                conn.rollback();
                throw new DBException("Unexpected error: no rows affected");
            }
        }
        catch(SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new DBException("ERROR in rollback: " + e1.getMessage());
            }
            throw new DBException(e.getMessage());
        }
        finally {
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public void update(Department department) {
        String query = "UPDATE department SET Name = ? WHERE Id = ?";
        ResultSet rs = null;

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, department.getName());
            pstmt.setInt(2, department.getId());
            pstmt.executeUpdate();
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally {
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public void deleteById(Integer ID) {
        String query = "DELETE FROM department WHERE Id = ?";
        ResultSet rs = null;

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, ID);
            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected == 0)
                throw new IllegalArgumentException("Department with Id " + ID + " doesn't exists");
        }
        catch(SQLException e) {
            throw new DBIntegrityException(e.getMessage());
        }
        finally {
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public Department findById(Integer ID) {
        String query = "SELECT Id AS DepartmentId, Name AS Department FROM department WHERE Id = ?";
        ResultSet rs = null;

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                Department department = DaoImplService.instantiateDepartment(rs);
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

    @Override
    public List<Department> findAll() {
        String query = "SELECT Id AS DepartmentId, Name AS Department FROM department";
        ResultSet rs = null;

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            rs = pstmt.executeQuery();

            List<Department> departments = new LinkedList<>();

            while(rs.next()) {
                departments.add(DaoImplService.instantiateDepartment(rs));
            }
            return departments;
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally {
            DBConfig.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findByDepartment(Integer departmentId) {
        String query = "SELECT Id AS DepartmentId, Name AS Department FROM department WHERE Id = ?";
        ResultSet rs = null;

        try(PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, departmentId);
            rs = pstmt.executeQuery();

            List<Department> department = new LinkedList<>();

            if(rs.next()) {
                department.add(DaoImplService.instantiateDepartment(rs));
            }
            return department;
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally {
            DBConfig.closeResultSet(rs);
        }
    }   
}