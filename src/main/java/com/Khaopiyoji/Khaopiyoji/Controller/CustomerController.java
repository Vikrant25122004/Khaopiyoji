package com.Khaopiyoji.Khaopiyoji.Controller;

import com.Khaopiyoji.Khaopiyoji.Entity.Customer;
import com.Khaopiyoji.Khaopiyoji.Entity.Subscriptions;
import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import com.Khaopiyoji.Khaopiyoji.Repository.VendorRepository;
import com.Khaopiyoji.Khaopiyoji.Service.CustomerService;
import com.Khaopiyoji.Khaopiyoji.Service.SubscriptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.razorpay.*;
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<?> customerbyusername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Customerusername = authentication.getName();
        try {
            Customer customer = customerService.CustomerByusername(Customerusername);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping
    public  ResponseEntity<?> deletebyusername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Customerusername = authentication.getName();
        customerService.deletecustomer(Customerusername);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/subscription/{vendorusername}")
    public ResponseEntity<?> createsubscription(@PathVariable String vendorusername){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try{
            Customer customer = customerService.CustomerByusername(username);
            long id = customer.getCustomerId();
            Vendors vendors = vendorRepository.findByvendorusername(vendorusername);
            long vid = vendors.getVendorId();
            String plan_id = String.valueOf(vid);
            Subscriptions subscriptions1 = subscriptionService.createsubscription(username, vendorusername);
            return new ResponseEntity<>(subscriptions1,HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/subscription")
    public ResponseEntity<?> getsubscription(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Customer customer = customerService.CustomerByusername(username);
            long id =  customer.getCustomerId();
            Subscriptions subscriptions = subscriptionService.getCustomersubscriptiondetails(id);
            return new ResponseEntity<>(subscriptions,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/renew")
    public ResponseEntity<?> renewsubs(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            Customer customer = customerService.CustomerByusername(username);
           long customerid = customer.getCustomerId();
            Subscriptions subscriptions = subscriptionService.renew(customerid);
            return new ResponseEntity<>(subscriptions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }



    }

}
