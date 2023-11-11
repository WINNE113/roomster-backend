package com.roomster.roomsterbackend.repository;

import com.roomster.roomsterbackend.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query("""
    select t from TokenEntity t inner join  UserEntity u on t.userToken.id = u.id 
    where u.id = :userId and (t.expired = false or t.revoked = false )
""")
    List<TokenEntity> findAllValidTokensByUser(Long userId);
    Optional<TokenEntity> findByToken(String token);
}
