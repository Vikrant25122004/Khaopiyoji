package com.Khaopiyoji.Khaopiyoji.Controller;

import com.Khaopiyoji.Khaopiyoji.Entity.Customer;
import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import com.Khaopiyoji.Khaopiyoji.Service.*;

import com.Khaopiyoji.Khaopiyoji.utils.Jwtutils;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.Multipart;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;

import java.util.Map;
import java.util.Objects;
@Tag(name = "Public APIs")
@Controller
@RequestMapping("/public")
public class PublicController {
    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private Customeruserdetailimpls customeruserdetailimpls;
    @Autowired
    private VendorDetailService vendorDetailService;
    @Autowired
    private Jwtutils jwtutils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping("create-vendor")
    public ResponseEntity<?> registerVendor(@RequestBody Vendors vendors){
        try {

            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {
            System.out.println("error" +  e.getMessage());
            return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
        }
    }
    // Method to show the signup form (GET request)

    @PostMapping("/create-customer")
    public ResponseEntity<?> registerCustomer(@RequestPart("customer") Customer customer, @RequestPart("imageFile") MultipartFile imageFile){
        try {

            customerService.createcustomer(customer,imageFile);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login-customer")
    public ResponseEntity<String> login(@RequestBody Customer customer) {
        try{

            UserDetails userDetails = customeruserdetailimpls.loadUserByUsername(customer.getCustomerusername());
            String jwt = jwtutils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            logger.error("Customer login error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect username or password");
            // Add logging here!
        }

    }
    @PostMapping("/login-vendorr")
    public ResponseEntity<String> loginvendor(@RequestBody Vendors vendors) {
        try{

            UserDetails userDetails = vendorDetailService.loadUserByUsername(vendors.getVendorusername());
            String jwt = jwtutils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e) {
            logger.error("Customer login error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect username or password");
            // Add logging here!

        }
    }



}
