package com.rento.service.rentoservice.service;

import com.rento.service.rentoservice.entity.transport.Transport;

import java.util.List;
import java.util.UUID;

public interface TransportService {
    Transport create(Transport transport);

    List<Transport> getTransportsByOwner(UUID userId);
}
