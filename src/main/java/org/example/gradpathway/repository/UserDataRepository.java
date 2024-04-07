package org.example.gradpathway.repository;

import org.example.gradpathway.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

    @Query("SELECT u FROM UserData u WHERE u.user.id = :userId")
    UserData findByUserId(int userId);
}
