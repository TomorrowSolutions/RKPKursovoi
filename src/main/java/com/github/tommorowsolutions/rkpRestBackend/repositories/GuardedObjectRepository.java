package com.github.tommorowsolutions.rkpRestBackend.repositories;

import com.github.tommorowsolutions.rkpRestBackend.models.GuardedObject;
import org.springframework.data.repository.CrudRepository;

public interface GuardedObjectRepository extends CrudRepository<GuardedObject,Long> {
}
