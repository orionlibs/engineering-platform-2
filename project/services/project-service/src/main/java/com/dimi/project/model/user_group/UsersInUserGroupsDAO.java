package com.dimi.project.model.user_group;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersInUserGroupsDAO extends JpaRepository<UserInUserGroupModel, UUID>
{
    @Query(value = "SELECT 1", nativeQuery = true)
    Integer testConnection();


    Optional<UserInUserGroupModel> findByUserGroupAndUserID(UserGroupModel userGroup, UUID userID);


    List<UserInUserGroupModel> findAllByUserID(UUID userID);


    List<UserInUserGroupModel> findAllByUserGroup_Id(UUID userGroupID);
}
