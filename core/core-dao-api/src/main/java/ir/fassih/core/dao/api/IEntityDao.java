package ir.fassih.core.dao.api;

import java.util.Optional;

public interface IEntityDao<T, I> {

    Optional<T> findById(I id);

    boolean save(T entity);

    boolean update(T entity);

    boolean delete(I id);

}
