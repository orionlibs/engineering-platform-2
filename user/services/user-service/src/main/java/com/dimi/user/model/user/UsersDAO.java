package com.dimi.user.model.user;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDAO extends JpaRepository<UserModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();
}
