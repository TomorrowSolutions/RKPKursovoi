package com.github.tommorowsolutions.rkpRestBackend.repositories;

import com.github.tommorowsolutions.rkpRestBackend.models.Service;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRepository extends CrudRepository<Service,Long> {
}
