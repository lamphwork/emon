package lamph.emon.common.repository;

import java.util.List;
import java.util.Optional;

public interface DomainRepo<T, ID> {

    Optional<T> findById(ID id);

    List<T> findAllByID(List<ID> ids);

    ID create(T t);

    void update(ID id, T t);

    void delete(ID id);
}
