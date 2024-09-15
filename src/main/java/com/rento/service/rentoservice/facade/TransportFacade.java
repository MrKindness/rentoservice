package com.rento.service.rentoservice.facade;

import com.rento.service.rentoservice.dto.SimpleResponseDto;
import com.rento.service.rentoservice.dto.transport.TransportCreateRequestDto;
import com.rento.service.rentoservice.dto.transport.TransportResponseDto;
import com.rento.service.rentoservice.dto.transport.TransportUpdateRequestDto;
import com.rento.service.rentoservice.entity.transport.Transport;
import com.rento.service.rentoservice.entity.transport.TransportStatus;
import com.rento.service.rentoservice.entity.user.User;
import com.rento.service.rentoservice.exception.ValidationException;
import com.rento.service.rentoservice.service.TransportService;
import com.rento.service.rentoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class TransportFacade {

    private final TransportService service;
    private final UserService userService;

    @Autowired
    public TransportFacade(TransportService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    public ResponseEntity<List<TransportResponseDto>> getAllTransports() {
        List<TransportResponseDto> response = this.service.getAllTransports().stream().map(TransportResponseDto::new).toList();

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<TransportResponseDto>> getTransportsByOwner(Authentication authentication) {
        String username = authentication.getName();
        User user = this.userService.getByUsername(username);

        List<TransportResponseDto> transports = this.service.getTransportsByOwner(user.getId()).stream().map(TransportResponseDto::new).toList();

        return ResponseEntity.ok().body(transports);
    }

    public ResponseEntity<List<TransportResponseDto>> getAvailableTransports(Authentication authentication) {
        UUID userId = null;
        if (Objects.nonNull(authentication)) {
            String username = authentication.getName();
            userId = this.userService.getByUsername(username).getId();
        }

        List<TransportResponseDto> transports = this.service.getAvailableTransports(userId).stream().map(TransportResponseDto::new).toList();

        return ResponseEntity.ok().body(transports);
    }

    public ResponseEntity<List<TransportResponseDto>> getRentedTransports(Authentication authentication) {
        String username = authentication.getName();
        User user = this.userService.getByUsername(username);

        List<TransportResponseDto> transports = this.service.getRentedTransports(user.getId()).stream().map(TransportResponseDto::new).toList();

        return ResponseEntity.ok().body(transports);
    }

    public ResponseEntity<SimpleResponseDto> rentTransport(Authentication authentication, UUID transportId) {
        String username = authentication.getName();
        User user = this.userService.getByUsername(username);

        this.service.rentTransport(user.getId(), transportId);

        return ResponseEntity.ok().body(new SimpleResponseDto(true, "the car was rented!"));
    }

    public ResponseEntity<SimpleResponseDto> create(TransportCreateRequestDto request) {
        Transport transport = createEntity(request);

        this.service.create(transport);

        return ResponseEntity.ok().body(new SimpleResponseDto(true, "entity created!"));
    }

    public ResponseEntity<TransportResponseDto> update(TransportUpdateRequestDto request) {
        Transport transport = createEntity(request);

        transport = this.service.update(transport);

        return ResponseEntity.ok().body(new TransportResponseDto(transport));
    }

    public ResponseEntity<SimpleResponseDto> delete(Authentication authentication, UUID transportId) {
        String username = authentication.getName();
        User user = this.userService.getByUsername(username);

        this.service.delete(user.getId(), transportId);

        return ResponseEntity.ok().body(new SimpleResponseDto(true, "entity deleted!"));
    }

    public Transport createEntity(TransportCreateRequestDto request) {
        Transport transport = new Transport();
        transport.setOwner(this.userService.getByUsername(request.getOwner()));
        transport.setBrand(request.getBrand());
        transport.setModel(request.getModel());
        transport.setYear(Integer.parseInt(request.getYear()));
        transport.setLocation(request.getLocation());
        transport.setAddress(request.getAddress());
        transport.setDescription(request.getDescription());

        return transport;
    }

    public Transport createEntity(TransportUpdateRequestDto request) {
        Transport transport = new Transport();
        transport.setId(UUID.fromString(request.getUuid()));
        transport.setBrand(request.getBrand());
        transport.setModel(request.getModel());
        transport.setYear(Integer.parseInt(request.getYear()));
        transport.setLocation(request.getLocation());
        transport.setAddress(request.getAddress());
        transport.setDescription(request.getDescription());

        TransportStatus status;
        try {
            status = TransportStatus.valueOf(request.getStatus());
        } catch (Exception ex) {
            throw new ValidationException("Invalid status!");
        }
        transport.setStatus(status);

        return transport;
    }
}
