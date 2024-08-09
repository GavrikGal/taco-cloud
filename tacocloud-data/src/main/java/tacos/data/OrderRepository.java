package tacos.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import tacos.TacoOrder;
import tacos.User;

import java.util.List;


public interface OrderRepository
        extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByUserOrderByIdDesc(User user, Pageable pageable);
}
