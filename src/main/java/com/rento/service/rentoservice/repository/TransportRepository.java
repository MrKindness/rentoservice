package com.rento.service.rentoservice.repository;

import com.rento.service.rentoservice.entity.transport.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransportRepository extends JpaRepository<Transport, UUID> {
    List<Transport> findAllByOwnerId(UUID id);
}
