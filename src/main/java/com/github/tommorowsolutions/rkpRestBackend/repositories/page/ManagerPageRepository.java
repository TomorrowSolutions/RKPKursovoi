package com.github.tommorowsolutions.rkpRestBackend.repositories.page;

import com.github.tommorowsolutions.rkpRestBackend.models.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ManagerPageRepository extends PagingAndSortingRepository<Manager, Long> {
    public Page<Manager> findTop5ByOrderByIdDesc(Pageable pageable);
}
