package model.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
    public void insert(Seller seller) { //inserir seller NO BANCO DE DADOS
        String query = "INSERT INTO seller VALUES (DEFAULT, ?, ?, ?, ?, ?)";
        ResultSet rs = null;

        try(PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);

            pstmt.setString(1, seller.getName());
            pstmt.setString(2, seller.getEmail());
            pstmt.setObject(3, seller.getBirthDate());
            pstmt.setBigDecimal(4, seller.getBaseSalary());
            pstmt.setInt(5, seller.getDepartment().getId());
            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected > 0) {
                ResultSet rowsResult = pstmt.getGeneratedKeys();
                if(rowsResult.next()) {
                    seller.setId(rowsResult.getInt(1));
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
    public void update(Seller seller) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteById(Integer ID) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Seller findById(Integer ID) {
        String query = "SELECT seller.*, department.Name AS Department FROM seller JOIN department ON " + 
                       "seller.DepartmentId = department.Id  WHERE seller.Id = ?";
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
        String query = "SELECT seller.*, department.Name AS Department FROM seller JOIN department ON " +
                       "seller.DepartmentId = department.Id ORDER BY seller.Name";
        ResultSet rs = null;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            rs = pstmt.executeQuery();

            List<Seller> sellers = new LinkedList<>();
            Map<Integer, Department> departments = new HashMap<>(); //para ter apenas um objeto department para cada department.

            while(rs.next()) {
                Department department = departments.get(rs.getInt("DepartmentId")); //se já existir, pega ele, NÃO CRIA UM NOVO.

                if(department == null) { //Se não existir, cria um novo e coloca no Map para não se repetir.
                    department = instantiateDepartment(rs);
                    departments.put(rs.getInt("DepartmentId"), department);
                }
                sellers.add(instantiateSeller(rs, department));
            }
            return sellers;
        }
        catch(SQLException e) {
            throw new DBException(e.getMessage());
        }
        finally {
            DBConfig.closeResultSet(rs);
        }
    }   

    @Override
    public List<Seller> findByDepartment(Integer departmentId) {
        String query = "SELECT seller.*, department.Name AS Department FROM seller JOIN department " + 
                       "ON seller.DepartmentId = department.Id WHERE seller.DepartmentId = ? ORDER BY seller.Name";
        ResultSet rs = null;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, departmentId);
            rs = pstmt.executeQuery();

            List<Seller> sellerList = new LinkedList<>();
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
        String query = "SELECT Id AS DepartmentId, Name AS Department FROM department WHERE Id = ?";
        ResultSet rs = null;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, departmentId);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                Department department = instantiateDepartment(rs);
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