package org.anthony.library.repository.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DaoInterface<T /*extends Entity<K>*/, K> {
    //Create
    K Create(T entity) throws SQLException;
    //Read
    public Optional<T> findById(K id) throws SQLException;
    public List<T> findAll() throws SQLException;
    //Update
    public T updateById(T entity) throws SQLException;
    //Delete
    public boolean deleteById(K id) throws SQLException;
}
