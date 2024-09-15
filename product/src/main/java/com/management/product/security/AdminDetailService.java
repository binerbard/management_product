package com.management.product.security;

import com.management.product.datasources.domain.schema.AdminDetailDTO;
import com.management.product.datasources.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AdminDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;

    public  AdminDetailService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account =  adminRepository.findByUsername(username).map(AdminDetailDTO::new).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new User(account.getUsername(),account.getPassword(), account.getAuthorities());
    }
}
