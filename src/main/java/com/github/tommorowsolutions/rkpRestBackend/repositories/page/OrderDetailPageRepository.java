package com.github.tommorowsolutions.rkpRestBackend.repositories.page;

import com.github.tommorowsolutions.rkpRestBackend.models.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderDetailPageRepository extends PagingAndSortingRepository<OrderDetail,Long> {
    public Page<OrderDetail> findTop5ByOrderByIdDesc(Pageable pageable);
}
