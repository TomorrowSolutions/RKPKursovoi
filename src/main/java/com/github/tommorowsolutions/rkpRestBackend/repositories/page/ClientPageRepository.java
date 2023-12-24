package com.github.tommorowsolutions.rkpRestBackend.repositories.page;

import com.github.tommorowsolutions.rkpRestBackend.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClientPageRepository extends PagingAndSortingRepository<Client,Long> {
    public Page<Client> findTop5ByOrderByIdDesc(Pageable pageable);
}
