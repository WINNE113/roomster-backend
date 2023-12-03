package com.roomster.roomsterbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roomster.roomsterbackend.entity.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    List<Tenant> findByRoomId(Long roomId);
}
