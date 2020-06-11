package autogara.persistence;

import autogara.domain.Entity;

import java.util.ArrayList;

public interface IRepository<ID, E extends Entity<ID>> {
    int size();
    void save(E entity);
    void delete(ID id);
    void update(ID id, E entity);
    E findOne(ID id);
    ArrayList<E> findAll();
}
