package com.github.tommorowsolutions.rkpRestBackend.repositories;

import com.github.tommorowsolutions.rkpRestBackend.models.OrderDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,Long> {
    @Query(value = "SELECT * FROM order_details WHERE order_id = ?1", nativeQuery = true)
    public List<OrderDetail> findByOrderId(Long id);
}
