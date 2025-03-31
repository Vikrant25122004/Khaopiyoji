package com.Khaopiyoji.Khaopiyoji.Service;

import com.Khaopiyoji.Khaopiyoji.Entity.Customer;
import com.Khaopiyoji.Khaopiyoji.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class Customeruserdetailimpls implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findBycustomerusername(username);
        if ( customer != null){
            UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                    .username(customer.getCustomerusername())
                    .password(customer.getPassword())
                    .roles("CUSTOMER")
                    .build();
            return userDetails;

        }
        throw new UsernameNotFoundException("user not found with user");
    }



}
