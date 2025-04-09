package com.Khaopiyoji.Khaopiyoji.Service;

import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import com.Khaopiyoji.Khaopiyoji.Repository.VendorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private redisService redisService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void createvendor(Vendors vendors){
        vendors.setPassword(passwordEncoder.encode(vendors.getPassword()));
        emailService.messages(vendors.getEmail(), "user registeration","thanks for being a vendor of Khapiyoji tiffin platform, hope u will enjoy");
        vendorRepository.save(vendors);
    }
    @Transactional
    public void deletevendor(String Vendorusername){
        Vendors vendors = vendorRepository.findByvendorusername(Vendorusername);
        if (vendors!=null) {
            vendorRepository.deleteByvendorusername(Vendorusername);
        }
        else {
            return;
        }
    }
    public Vendors vendorsbyusername(String vendorusername) throws JsonProcessingException {
        Vendors vendors = redisService.get(vendorusername,Vendors.class);
        if(vendors!=null){
            return vendors;
        }
        else {
            Vendors vendors1 = vendorRepository.findByvendorusername(vendorusername);
            redisService.setLog(vendorusername,vendors1,300l);
            return vendors1;
        }
    }
}
