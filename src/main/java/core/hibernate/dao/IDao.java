package core.hibernate.dao;

import java.util.List;

public interface IDao<E> {

    List<E> findAll();
    E findById(int id);
    void save(E entity);
    void update(E entity);
    void delete(E entity);
}
