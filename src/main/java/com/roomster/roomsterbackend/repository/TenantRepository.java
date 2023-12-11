package com.roomster.roomsterbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roomster.roomsterbackend.entity.Tenant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    List<Tenant> findByRoomId(Long roomId);

    @Query("SELECT COUNT(t) FROM Tenant t " +
            "WHERE t.email = :email " +
            "AND (:tenantId IS NULL OR t.id != :tenantId)")
    Long countSameEmail(@Param("email") String email, @Param("tenantId") Long tenantId);

    @Query("SELECT COUNT(t) FROM Tenant t " +
            "WHERE t.phoneNumber = :phoneNumber " +
            "AND (:tenantId IS NULL OR t.id != :tenantId)")
    Long countSamePhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("tenantId") Long tenantId);

    @Query("SELECT COUNT(t) FROM Tenant t " +
            "WHERE t.identifier = :identifier " +
            "AND (:tenantId IS NULL OR t.id != :tenantId)")
    Long countSameIdentifier(@Param("identifier") String identifier, @Param("tenantId") Long tenantId);
}
