package com.dimi.user.model.authority;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthoritiesDAO extends JpaRepository<UserAuthorityModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    Optional<UserAuthorityModel> findByAuthority(String authority);
}
