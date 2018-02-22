package com.plankdev.jwtsecurity;

import com.plankdev.jwtsecurity.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRespository extends JpaRepository<Authority, Long>{

}
