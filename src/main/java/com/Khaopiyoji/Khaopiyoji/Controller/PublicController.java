package com.Khaopiyoji.Khaopiyoji.Controller;

import com.Khaopiyoji.Khaopiyoji.Entity.Customer;
import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import com.Khaopiyoji.Khaopiyoji.Service.*;

import com.Khaopiyoji.Khaopiyoji.utils.Jwtutils;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import jakarta.mail.Multipart;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;

import java.util.Map;
import java.util.Objects;
@CrossOrigin
@RestController
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
    @PostMapping("/create-customer")
    public ResponseEntity<?> registerCustomer(@RequestPart Customer customer, @RequestPart MultipartFile imageFile){
        try {

            customerService.createcustomer(customer,imageFile);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login-customr")
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
    @PostMapping("/create-order")
    public ResponseEntity<?> createorder()throws Exception{

        RazorpayClient client = new RazorpayClient("rzp_test_8LZboHNs3lEOMm","4QpAyV4JCZ9XkjbazDUC1Wa0");
        JSONObject object = new JSONObject();
        object.put("amount",10000);
        object.put("currency","INR");
        object.put("receipt","txn_235425");
        Order order = client.orders.create(object);
        System.out.println(order);
        return new ResponseEntity<>(order,HttpStatus.OK);

    }


}
