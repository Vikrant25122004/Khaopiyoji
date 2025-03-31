package com.Khaopiyoji.Khaopiyoji.Service;

import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import com.Khaopiyoji.Khaopiyoji.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VendorDetailService implements UserDetailsService {
    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Vendors vendors = vendorRepository.findByvendorusername(username);
        if ( vendors != null){
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(vendors.getVendorusername())
                    .password(vendors.getPassword())
                    .roles("VENDORS")
                    .build();
            return userDetails;

        }
        throw new UsernameNotFoundException("user not found with user");
    }
}
