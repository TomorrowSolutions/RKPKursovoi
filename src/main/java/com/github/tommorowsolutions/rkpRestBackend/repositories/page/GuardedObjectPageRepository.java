package com.github.tommorowsolutions.rkpRestBackend.repositories.page;

import com.github.tommorowsolutions.rkpRestBackend.models.GuardedObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GuardedObjectPageRepository extends PagingAndSortingRepository<GuardedObject,Long> {
    public Page<GuardedObject> findTop5ByOrderByIdDesc(Pageable pageable);
}
