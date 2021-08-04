package com.company.nc_project.service;

import com.company.nc_project.model.Client;
import com.company.nc_project.repository.ClientRepository;
import com.company.nc_project.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public Client getClientFromRequest(HttpServletRequest request) {
        String email = jwtTokenProvider.getUserName(jwtTokenProvider.resolveToken(request));
        return clientRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("No such client"));
    }
}
