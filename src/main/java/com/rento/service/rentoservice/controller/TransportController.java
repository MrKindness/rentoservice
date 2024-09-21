package com.rento.service.rentoservice.controller;

import com.rento.service.rentoservice.dto.SimpleResponseDto;
import com.rento.service.rentoservice.dto.transport.TransportCreateRequestDto;
import com.rento.service.rentoservice.dto.transport.TransportResponseDto;
import com.rento.service.rentoservice.dto.transport.TransportUpdateRequestDto;
import com.rento.service.rentoservice.facade.TransportFacade;
import com.rento.service.rentoservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Constants.API.TRANSPORT.ROOT)
@CrossOrigin
public class TransportController {

    private final TransportFacade facade;

    @Autowired
    public TransportController(TransportFacade facade) {
        this.facade = facade;
    }

    @GetMapping(Constants.API.TRANSPORT.OWNER_TRANSPORTS)
    public ResponseEntity<List<TransportResponseDto>> getUserTransports(Authentication authentication) {
        return this.facade.getTransportsByOwner(authentication);
    }

    @GetMapping(Constants.API.TRANSPORT.ADMIN)
    public ResponseEntity<List<TransportResponseDto>> getAllTransports() {
        return this.facade.getAllTransports();
    }

    @GetMapping(Constants.API.TRANSPORT.AVAILABLE_TRANSPORTS)
    public ResponseEntity<List<TransportResponseDto>> getAvailableTransports(Authentication authentication) {
        return this.facade.getAvailableTransports(authentication);
    }

    @GetMapping(Constants.API.TRANSPORT.RENTED_TRANSPORTS)
    public ResponseEntity<List<TransportResponseDto>> getRentedTransports(Authentication authentication) {
        return this.facade.getRentedTransports(authentication);
    }

    @PutMapping(Constants.API.TRANSPORT.RENT_TRANSPORT)
    public ResponseEntity<SimpleResponseDto> rentTransport(Authentication authentication, @PathVariable UUID transportId) {
        return this.facade.rentTransport(authentication, transportId);
    }

    @PostMapping
    public ResponseEntity<SimpleResponseDto> create(@RequestBody TransportCreateRequestDto request) {
        return this.facade.create(request);
    }

    @PutMapping
    public ResponseEntity<TransportResponseDto> update(@RequestBody TransportUpdateRequestDto request) {
        return this.facade.update(request);
    }

    @DeleteMapping(Constants.API.TRANSPORT.TRANSPORT_BY_ID)
    public ResponseEntity<SimpleResponseDto> delete(Authentication authentication, @PathVariable UUID transportId) {
        return this.facade.delete(authentication, transportId);
    }
}
