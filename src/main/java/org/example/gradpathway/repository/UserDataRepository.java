package org.example.gradpathway.repository;

import org.example.gradpathway.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

    UserData findByUserId(int userId);
}
