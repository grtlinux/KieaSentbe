package org.tain.repository.apis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.tain.domain.apis.Apis;

@RepositoryRestResource
public interface ApisRepository extends JpaRepository<Apis, Long>{

}
