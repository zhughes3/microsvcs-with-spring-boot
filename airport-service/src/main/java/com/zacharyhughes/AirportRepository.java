package com.zacharyhughes;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "airports", path = "airports")
public interface AirportRepository extends PagingAndSortingRepository<Airport, Long> {
	Airport findByIata(@Param("iata") String iata);
}
