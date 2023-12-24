package com.github.tommorowsolutions.rkpRestBackend.repositories.page;


import com.github.tommorowsolutions.rkpRestBackend.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface OrderPageRepository extends PagingAndSortingRepository<Order,Long> {
    public Page<Order> findTop5ByOrderByIdDesc(Pageable pageable);
}
