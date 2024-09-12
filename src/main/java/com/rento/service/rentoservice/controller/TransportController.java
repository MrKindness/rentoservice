package com.rento.service.rentoservice.controller;

import com.rento.service.rentoservice.dto.SimpleResponseDto;
import com.rento.service.rentoservice.dto.transport.TransportRequestDto;
import com.rento.service.rentoservice.dto.transport.TransportResponseDto;
import com.rento.service.rentoservice.facade.TransportFacade;
import com.rento.service.rentoservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<SimpleResponseDto> create(@RequestBody TransportRequestDto request) {
        return this.facade.create(request);
    }
}
