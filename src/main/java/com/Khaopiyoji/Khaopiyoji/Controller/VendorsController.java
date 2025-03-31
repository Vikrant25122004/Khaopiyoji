package com.Khaopiyoji.Khaopiyoji.Controller;

import com.Khaopiyoji.Khaopiyoji.Entity.Subscriptions;
import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import com.Khaopiyoji.Khaopiyoji.Repository.VendorRepository;
import com.Khaopiyoji.Khaopiyoji.Service.SubscriptionService;
import com.Khaopiyoji.Khaopiyoji.Service.VendorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Vendor")
public class VendorsController {
    @Autowired
    private VendorService vendorService;
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<?> Vendorbyusername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Vendorusername = authentication.getName();
        try {
            Vendors vendors = vendorService.vendorsbyusername(Vendorusername);
            return new ResponseEntity<>(vendors, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deletebyusername() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String Vendorusername = authentication.getName();
            vendorService.deletevendor(Vendorusername);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/subscription")
    public ResponseEntity<?> subsbyvendor(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Vendors vendors = vendorService.vendorsbyusername(username);
            long vendorid = vendors.getVendorId();
            List<Subscriptions> subscriptionsList = subscriptionService.subscriptionsbyvendor(vendorid);
            return new ResponseEntity<>(subscriptionsList,HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }

    }
}

