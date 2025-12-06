package com.hms.dao;

import java.util.List;

public interface CrudRepository<T> {
    boolean add(T obj);
    boolean update(T obj);
    boolean delete(int id);
    T findById(int id);
    List<T> findAll();
}
