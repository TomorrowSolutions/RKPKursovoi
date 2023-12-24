package com.github.tommorowsolutions.rkpRestBackend.repositories;

import com.github.tommorowsolutions.rkpRestBackend.models.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client,Long> {
}
