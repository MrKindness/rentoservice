package com.rento.service.rentoservice.repository;

import com.rento.service.rentoservice.entity.transport.Transport;
import com.rento.service.rentoservice.entity.transport.TransportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransportRepository extends JpaRepository<Transport, UUID> {
    List<Transport> findAllByOwnerId(UUID ownerId);

    List<Transport> findAllByOwnerIdNotAndStatus(UUID ownerId, TransportStatus status);

    List<Transport> findAllByStatus(TransportStatus status);

    List<Transport> findAllByRenterId(UUID renterId);
}
