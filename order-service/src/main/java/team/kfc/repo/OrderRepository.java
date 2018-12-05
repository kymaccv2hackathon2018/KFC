package team.kfc.repo;

import org.springframework.data.repository.CrudRepository;

import team.kfc.web.Order;

/**
 *
 */
public interface OrderRepository extends CrudRepository<Order, String>
{
	Order findOrderById(String orderId);
}
