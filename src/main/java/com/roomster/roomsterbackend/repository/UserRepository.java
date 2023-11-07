package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM users " +
            " WHERE id =:userId", nativeQuery = true)
    UserEntity getUserEntityByUserId(@Param("userId") Long userId);
}