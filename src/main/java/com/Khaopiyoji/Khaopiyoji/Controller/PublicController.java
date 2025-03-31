package com.Khaopiyoji.Khaopiyoji.Controller;

import com.Khaopiyoji.Khaopiyoji.Entity.Customer;
import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import com.Khaopiyoji.Khaopiyoji.Service.CustomerService;
import com.Khaopiyoji.Khaopiyoji.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VendorService vendorService;
    @PostMapping("create-vendor")
    public ResponseEntity<?> registerVendor(@RequestBody Vendors vendors){
        try {
            vendorService.createvendor(vendors);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println("error" +  e.getMessage());
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("create-customer")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer){
        try {
            customerService.createcustomer(customer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }

}
