package com.rento.service.rentoservice.facade;

import com.rento.service.rentoservice.dto.SimpleResponseDto;
import com.rento.service.rentoservice.dto.transport.TransportRequestDto;
import com.rento.service.rentoservice.dto.transport.TransportResponseDto;
import com.rento.service.rentoservice.entity.transport.Transport;
import com.rento.service.rentoservice.entity.user.User;
import com.rento.service.rentoservice.service.TransportService;
import com.rento.service.rentoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransportFacade {

    private final TransportService service;
    private final UserService userService;

    @Autowired
    public TransportFacade(TransportService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    public ResponseEntity<List<TransportResponseDto>> getTransportsByOwner(Authentication authentication) {
        String username = authentication.getName();
        User user = this.userService.getByUsername(username);

        List<TransportResponseDto> transports = this.service.getTransportsByOwner(user.getId())
                .stream()
                .map(TransportResponseDto::new)
                .toList();

        return ResponseEntity.ok().body(transports);
    }

    public ResponseEntity<SimpleResponseDto> create(TransportRequestDto request) {
        Transport transport = createEntity(request);

        this.service.create(transport);

        return ResponseEntity.ok().body(new SimpleResponseDto(true, "entity created!"));
    }

    public Transport createEntity(TransportRequestDto request) {
        Transport transport = new Transport();
        transport.setOwner(this.userService.getByUsername(request.getOwner()));
        transport.setBrand(request.getBrand());
        transport.setModel(request.getModel());
        transport.setYear(Short.parseShort(request.getYear()));
        transport.setLocation(request.getLocation());
        transport.setAddress(request.getAddress());
        transport.setDescription(request.getDescription());

        return transport;
    }
}
