package model.dao;

import java.util.List;
import model.entities.Seller;

public interface GenericDao<T> {
    void insert(T object);
    void update(T object);
    void deleteById(Integer ID);
    T findById(Integer ID);
    List<T> findAll();
    List<Seller> findByDepartment(Integer departmentId); //mudar isso depois, talvez.
}