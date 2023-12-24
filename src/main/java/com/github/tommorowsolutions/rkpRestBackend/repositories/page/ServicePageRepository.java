package com.github.tommorowsolutions.rkpRestBackend.repositories.page;


import com.github.tommorowsolutions.rkpRestBackend.models.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ServicePageRepository extends PagingAndSortingRepository<Service,Long> {
    public Page<Service> findTop5ByOrderByIdDesc(Pageable pageable);
}
