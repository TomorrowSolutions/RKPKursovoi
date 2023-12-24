package com.github.tommorowsolutions.rkpRestBackend.repositories;

import com.github.tommorowsolutions.rkpRestBackend.models.Manager;
import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager,Long> {
}
