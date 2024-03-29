package model.dao;

import java.util.List;

public interface GenericDao<T> {
    void insert(T object);
    void update(T object);
    void deleteById(Integer ID);
    T findById(Integer ID);
    List<T> findAll();
    List<T> findByDepartment(Integer departmentId); //não é o ideal para esse caso.
}