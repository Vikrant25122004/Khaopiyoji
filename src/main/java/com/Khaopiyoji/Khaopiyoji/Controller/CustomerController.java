package com.Khaopiyoji.Khaopiyoji.Controller;

import com.Khaopiyoji.Khaopiyoji.Entity.Customer;
import com.Khaopiyoji.Khaopiyoji.Entity.Subscriptions;
import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import com.Khaopiyoji.Khaopiyoji.Repository.VendorRepository;
import com.Khaopiyoji.Khaopiyoji.Service.CustomerService;
import com.Khaopiyoji.Khaopiyoji.Service.SubscriptionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.Khaopiyoji.Khaopiyoji.Entity.myorder;
import com.Khaopiyoji.Khaopiyoji.Repository.myorderrepository;

import java.time.LocalDateTime;
import java.util.Map;


@Tag(name = "Customer APIs")
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private myorderrepository myorderrepository;

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
    @PostMapping("/subscribe/{vendorusername}")
    public ResponseEntity<?> subscribe(@PathVariable String vendorusername) throws RazorpayException, JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Customer customer = customerService.CustomerByusername(username);
        if(customer.getSubscriptionid()!=0){
            return new ResponseEntity<>("you already have subscription",HttpStatus.ALREADY_REPORTED);
        }
        else {
            Vendors vendors = vendorRepository.findByvendorusername(vendorusername);
            RazorpayClient client = new RazorpayClient("rzp_test_8LZboHNs3lEOMm", "4QpAyV4JCZ9XkjbazDUC1Wa0");
            JSONObject object = new JSONObject();
            object.put("amount", vendors.getSubscription_price());
            object.put("currency", "INR");
            object.put("receipt", LocalDateTime.now());
            Order order = client.orders.create(object);
            myorder myorder = new myorder();
            myorder.setAmount(order.get("amount"));

            myorder.setPaymentId(null);
            myorder.setStatus("created");
            myorder.setVendorusername(vendorusername);
            myorder.setOrderid(order.get("id"));
            myorder.setCustomerusername(username);
            myorder.setReciept(order.get("receipt"));
            myorderrepository.save(myorder);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }

    }
    @PostMapping("/subscription/")
    public ResponseEntity<?> createsubscription(@RequestBody Map<String, Object> data) throws JsonProcessingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        myorder myorder1 = this.myorderrepository.findByMyOrderid((Long) data.get("order_id"));
        myorder1.setPaymentId(data.get("payment_id").toString());
        myorder1.setStatus(data.get("status").toString());
        myorderrepository.save(myorder1);
        try{
            if(myorder1.getStatus()=="Paid"||myorder1.getStatus()=="paid") {
                Customer customer = customerService.CustomerByusername(username);
                long id = customer.getCustomerId();
                String vendorusername = myorder1.getVendorusername();
                Vendors vendors = vendorRepository.findByvendorusername(vendorusername);
                long vid = vendors.getVendorId();
                String plan_id = String.valueOf(vid);

                Subscriptions subscriptions1 = subscriptionService.createsubscription(username, vendorusername);
                return new ResponseEntity<>(subscriptions1, HttpStatus.CREATED);
            }
            else {
                throw new RuntimeException("payment not Successful");

            }

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
    @PostMapping("/renew/order")
    public ResponseEntity<?> reneew() throws JsonProcessingException, RazorpayException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Customer customer = customerService.CustomerByusername(username);

        String vendorusername = customer.getSubscriptionVendorUsername();
        Vendors vendors = vendorRepository.findByvendorusername(vendorusername);
        RazorpayClient client = new RazorpayClient("rzp_test_8LZboHNs3lEOMm", "4QpAyV4JCZ9XkjbazDUC1Wa0");
        JSONObject object = new JSONObject();
        object.put("amount", vendors.getSubscription_price());
        object.put("currency", "INR");
        object.put("receipt", LocalDateTime.now());
        Order order = client.orders.create(object);
        myorder myorder = new myorder();
        myorder.setAmount(order.get("amount"));
        myorder.setOrderid(order.get("id"));
        myorder.setPaymentId(null);
        myorder.setStatus("created");
        myorder.setVendorusername(vendorusername);
        myorder.setCustomerusername(username);
        myorder.setReciept(order.get("receipt"));
        myorderrepository.save(myorder);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }
    @PutMapping("/renew")
    public ResponseEntity<?> renewsubs(@RequestBody Map<String,Object> data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        myorder myorder1 = this.myorderrepository.findByMyOrderid((Long) data.get("order_id"));
        myorder1.setPaymentId(data.get("payment_id").toString());
        myorder1.setStatus(data.get("status").toString());
        myorderrepository.save(myorder1);
        try{
            if(myorder1.getStatus()=="Paid"||myorder1.getStatus()=="paid") {
                Customer customer = customerService.CustomerByusername(username);
                long id = customer.getCustomerId();
                String vendorusername = myorder1.getVendorusername();
                Vendors vendors = vendorRepository.findByvendorusername(vendorusername);
                long vid = vendors.getVendorId();
                String plan_id = String.valueOf(vid);
                Subscriptions subscriptions1 = subscriptionService.renew(id);
                return new ResponseEntity<>(subscriptions1, HttpStatus.CREATED);
            }
            else {
                throw new RuntimeException("payment not Successful");

            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping("/deleteSubscribing/{subscription_id}")
    public ResponseEntity<?> deleteRes(@PathVariable Long subscription_id){
        subscriptionService.deletesubs(subscription_id);
        return new ResponseEntity<>("you have no subscribed vendor",HttpStatus.NO_CONTENT);
    }




}
