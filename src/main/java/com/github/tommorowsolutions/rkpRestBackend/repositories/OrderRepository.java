package com.github.tommorowsolutions.rkpRestBackend.repositories;

import com.github.tommorowsolutions.rkpRestBackend.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}
