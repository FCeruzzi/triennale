package it.unibas.dbtesi.persistenza;

import java.util.List;

public interface IDAOGenerico<T> {
    
    public T findById(Long id, boolean lock) throws DAOException;
    
    public List<T> findAll() throws DAOException;
    
    public List<T> findAll(int offset, int limite) throws DAOException;

    public T makePersistent(T entity) throws DAOException;
    
    public void makeTransient(T entity) throws DAOException;
    
    public void lock(T entity) throws DAOException;
}
