package com.rento.service.rentoservice.service.impl;

import com.rento.service.rentoservice.entity.transport.Transport;
import com.rento.service.rentoservice.entity.transport.TransportStatus;
import com.rento.service.rentoservice.exception.ValidationException;
import com.rento.service.rentoservice.repository.TransportRepository;
import com.rento.service.rentoservice.repository.UserRepository;
import com.rento.service.rentoservice.service.TransportService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TransportServiceImpl implements TransportService {

    private final TransportRepository repository;
    private final UserRepository userRepository;

    @Autowired
    public TransportServiceImpl(TransportRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transport> getTransportsByOwner(UUID userId) {
        return this.repository.findAllByOwnerId(userId);
    }

    @Transactional
    public Transport create(Transport transport) {
        validateTransport(transport);
        transport.setStatus(TransportStatus.PENDING);

        return this.repository.save(transport);
    }

    private void validateTransport(Transport transport) {
        if (Objects.nonNull(transport.getId())) {
            throw new ValidationException("Invalid id!");
        }

        if (Objects.isNull(transport.getOwner()) ||
                Objects.isNull(transport.getOwner().getId()) ||
                !this.userRepository.existsById(transport.getOwner().getId())
        ) {
            throw new ValidationException("Invalid owner!");
        }

        if (StringUtils.isEmpty(transport.getBrand())) {
            throw new ValidationException("Invalid brand!");
        }

        if (StringUtils.isEmpty(transport.getModel())) {
            throw new ValidationException("Invalid model!");
        }

        if (Objects.isNull(transport.getYear()) || transport.getYear() > 2030 || transport.getYear() < 1900) {
            throw new ValidationException("Invalid year!");
        }
    }
}
