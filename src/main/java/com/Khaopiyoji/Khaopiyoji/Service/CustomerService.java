package com.Khaopiyoji.Khaopiyoji.Service;

import com.Khaopiyoji.Khaopiyoji.Entity.Customer;
import com.Khaopiyoji.Khaopiyoji.Repository.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private redisService redisService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void createcustomer(Customer customer, MultipartFile imageFile) throws IOException {
        customer.setImageName(imageFile.getOriginalFilename());
        customer.setImageType(imageFile.getContentType());
        customer.setImageData(imageFile.getBytes());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
    }
    @Transactional
    public void deletecustomer(String Customerusername){
        customerRepository.deleteBycustomerusername(Customerusername);
    }

    public Customer CustomerByusername(String Customerusername) throws JsonProcessingException {
        Customer customer = redisService.get(Customerusername,Customer.class);
        if(customer != null){
            return customer;
        }
        else {
            Customer customer1 = customerRepository.findBycustomerusername(Customerusername);
            redisService.setLog(Customerusername,customer1,300l);
         return customer1;
        }
    }


}
