package com.company.nc_project.security;

import com.company.nc_project.model.Client;
import com.company.nc_project.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("JwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    @Autowired
    public JwtUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Client doesn't exist"));
        return JwtUser.fromClient(client);
    }

}
