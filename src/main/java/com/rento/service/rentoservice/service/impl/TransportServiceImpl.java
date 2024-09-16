package com.rento.service.rentoservice.service.impl;

import com.rento.service.rentoservice.entity.transport.Transport;
import com.rento.service.rentoservice.entity.transport.TransportStatus;
import com.rento.service.rentoservice.entity.user.User;
import com.rento.service.rentoservice.exception.TransportNotFoundException;
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
import java.util.Optional;
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
    public List<Transport> getAllTransports() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public List<Transport> getPendingTransports() {
        return this.repository.findAllByStatus(TransportStatus.PENDING);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transport> getAvailableTransports(UUID userId) {
        return this.repository.findAllByOwnerIdNotAndStatus(userId, TransportStatus.PENDING);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transport> getTransportsByOwner(UUID userId) {
        return this.repository.findAllByOwnerId(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Transport getById(UUID transportId) {
        return this.repository.findById(transportId).orElseThrow(
                () -> new TransportNotFoundException(transportId.toString())
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<Transport> getRentedTransports(UUID userId) {
        return this.repository.findAllByRenterId(userId);
    }

    @Transactional
    @Override
    public void rentTransport(UUID userId, UUID transportId) {
        Transport transport = getById(transportId);

        if (Objects.nonNull(transport.getRenter()) && transport.getRenter().getId().equals(userId)) {
            return;
        }

        transport.setRenter(new User(userId));
        validateRentTransport(transport);
        transport.setStatus(TransportStatus.IN_USE);

        this.repository.save(transport);
    }

    @Transactional
    public Transport create(Transport transport) {
        validateCreateTransport(transport);
        transport.setStatus(TransportStatus.PENDING);

        return this.repository.save(transport);
    }

    @Transactional
    @Override
    public Transport update(Transport transport) {
        validateUpdateTransport(transport);

        Transport dbTransport = getById(transport.getId());

        if (!dbTransport.getStatus().equals(transport.getStatus())
                && TransportStatus.IN_USE.equals(transport.getStatus())) {
            throw new ValidationException("Invalid status!");
        }

        dbTransport.setBrand(transport.getBrand());
        dbTransport.setModel(transport.getModel());
        dbTransport.setYear(transport.getYear());
        dbTransport.setLocation(transport.getLocation());
        dbTransport.setAddress(transport.getAddress());
        dbTransport.setStatus(transport.getStatus());
        dbTransport.setDescription(transport.getDescription());
        if (TransportStatus.SERVICE.equals(transport.getStatus())
                || TransportStatus.PENDING.equals(transport.getStatus())) {
            dbTransport.setRenter(null);
        }

        return this.repository.save(dbTransport);
    }

    @Transactional
    @Override
    public void delete(UUID userId, UUID transportId) {
        validateDeleteTransport(userId, transportId);

        this.repository.deleteById(transportId);
    }

    private void validateCreateTransport(Transport transport) {
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

    private void validateUpdateTransport(Transport transport) {
        if (Objects.isNull(transport.getId()) || !this.repository.existsById(transport.getId())) {
            throw new ValidationException("Invalid id!");
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

    private void validateRentTransport(Transport transport) {
        if (TransportStatus.IN_USE.equals(transport.getStatus())) {
            throw new ValidationException("Transport is already in use!");
        }

        Optional<User> user = this.userRepository.findById(transport.getRenter().getId());
        if (user.isEmpty() || user.get().getRoles().iterator().next().getAuthority().equals("admin")) {
            throw new ValidationException("Invalid user!");
        }
    }

    private void validateDeleteTransport(UUID userId, UUID transportId) {
        Optional<User> user = this.userRepository.findById(userId);
        Transport transport = getById(transportId);

        if (user.isEmpty() ||
                (user.get().getRoles().iterator().next().getName().equals("user") && !transport.getOwner().getId().equals(userId))
        ) {
            throw new ValidationException("invalid user!");
        }
    }
}
