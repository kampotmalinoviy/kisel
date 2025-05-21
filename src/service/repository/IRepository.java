package service.repository;

public interface IRepository<T> {

    boolean create(T entity);
    boolean update(T entity);
    boolean delete(int id);
    void close();

}
