package com.rento.service.rentoservice.service;

import com.rento.service.rentoservice.entity.transport.Transport;

import java.util.List;
import java.util.UUID;

public interface TransportService {
    List<Transport> getAllTransports();

    List<Transport> getTransportsByOwner(UUID userId);

    List<Transport> getAvailableTransports(UUID userId);

    List<Transport> getRentedTransports(UUID userId);

    Transport getById(UUID transportId);

    void rentTransport(UUID userId, UUID transportId);

    Transport create(Transport transport);

    Transport update(Transport transport);

    void delete(UUID userId, UUID transportId);
}
