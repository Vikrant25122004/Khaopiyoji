package com.Khaopiyoji.Khaopiyoji.Service;

import com.Khaopiyoji.Khaopiyoji.Entity.Customer;
import com.Khaopiyoji.Khaopiyoji.Entity.Subscriptions;
import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import com.Khaopiyoji.Khaopiyoji.Repository.CustomerRepository;
import com.Khaopiyoji.Khaopiyoji.Repository.SubscriptionRepository;
import com.Khaopiyoji.Khaopiyoji.Repository.VendorRepository;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private EmailService emailService;

    public Subscriptions createsubscription(String customerusername, String vendorusername) throws RazorpayException {
        Customer customer = customerRepository.findBycustomerusername(customerusername);
        if (customer == null) {
            throw new RuntimeException("no customer with this username");
        }
        Vendors vendors = vendorRepository.findByvendorusername(vendorusername);
        if (vendors == null) {
            throw new RuntimeException("no vendor with this username");
        }

        Subscriptions subscriptions = new Subscriptions();
        subscriptions.setActive(true);
        subscriptions.setStartDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 1);
        subscriptions.setVendorusername(vendorusername);
        subscriptions.setCustomerusername(customerusername);
        subscriptions.setEndDate(calendar.getTime());
        subscriptions.setCustomerId(customer.getCustomerId());
        subscriptions.setVendorId(vendors.getVendorId());
        Subscriptions subscriptions1 = subscriptionRepository.save(subscriptions);

        customer.setSubscriptionVendorUsername(vendorusername);
        customer.setEndDate(subscriptions1.getEndDate());
        customer.setSubscriptionIsActive(subscriptions1.isActive());
        customer.setStartDate(subscriptions1.getStartDate());
        customer.setSubscriptionid(subscriptions1.getSubscriptionid());

        emailService.messages(customer.getEmail(),"regarding subscription","you have successfully subscribed your desired vendor");
        customerRepository.save(customer);
        return subscriptions1;

    }

    public Subscriptions getCustomersubscriptiondetails(long customerid) {
        Subscriptions subscriptions = subscriptionRepository.findBycustomerId(customerid);
        if (subscriptions == null) {
            throw new RuntimeException("you have not subscribed any vendor yet");
        }
        return subscriptions;
    }
    public  void deleteSubscription(Long subscriptionId){
        Subscriptions subscriptions = subscriptionRepository.findBysubscriptionid(subscriptionId);
        if(subscriptions.isActive()==false) {
            long customerid = subscriptions.getCustomerId();
            Customer customer = customerRepository.findBycustomerId(customerid);
            subscriptionRepository.deleteById(subscriptionId);
            customer.setSubscriptionid(0);
            customer.setSubscriptionVendorUsername(null);
            customer.setEndDate(null);
            customer.setStartDate(null);
            customer.setSubscriptionIsActive(false);
        }
    }
    public void deletesubs(Long subscriptionId){
        Subscriptions subscriptions = subscriptionRepository.findBysubscriptionid(subscriptionId);
        long customerid = subscriptions.getCustomerId();
        Customer customer = customerRepository.findBycustomerId(customerid);
        subscriptionRepository.deleteById(subscriptionId);
        customer.setSubscriptionid(0);
        customer.setSubscriptionVendorUsername(null);
        customer.setEndDate(null);
        customer.setStartDate(null);
        customer.setSubscriptionIsActive(false);

    }
    @Scheduled(cron = "0 0 12 * * ?")
    public void checkSubscriptionExpiry(){
        LocalDate today = LocalDate.now();
        LocalDate reminderDate = today.plusDays(3);
        LocalDate deleting = reminderDate.plusDays(6);
        List<Subscriptions> subscriptions = subscriptionRepository.findAll();
        for (Subscriptions subscriptions1 : subscriptions){
            LocalDate enddate = subscriptions1.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (enddate.isEqual(reminderDate)){
                sendexpry(subscriptions1);

            } else if (enddate==today) {
                deactivatesubs(subscriptions1);


            } else if (today.isEqual(deleting)) {
                deleteSubscription(subscriptions1.getSubscriptionid());

            }
        }
    }
    private void sendexpry(Subscriptions subscriptions) {
        long customerid = subscriptions.getCustomerId();
        Customer customer = customerRepository.findBycustomerId(customerid);
        long vendorid = subscriptions.getVendorId();
        Vendors vendors = vendorRepository.findByvendorId(vendorid);
        String subject = "Subcription Expiry Reminder";
        String text = "Your subscription with " + customer.getCustomerusername() + " is expiring soon";
        emailService.messages(customer.getEmail(), subject, text);

    }
    public void deactivatesubs(Subscriptions subscriptions){
        subscriptions.setActive(false);
        subscriptionRepository.save(subscriptions);

    }
    public Subscriptions renew(long customerid){
        Subscriptions subscriptions = subscriptionRepository.findBycustomerId(customerid);
        subscriptions.setActive(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(subscriptions.getEndDate());
        calendar.add(Calendar.MONTH,1);
        Date newenddate = calendar.getTime();
        subscriptions.setEndDate(newenddate);
        Subscriptions updatesubs = subscriptionRepository.save(subscriptions);
        Customer customer = customerRepository.findBycustomerId(subscriptions.getCustomerId());
        customer.setEndDate(updatesubs.getEndDate());
        customer.setSubscriptionIsActive(updatesubs.isActive());
        customerRepository.save(customer);
        emailService.messages(customer.getEmail(),"renew of subscription","you have successfully renewec ur subscription");
        return updatesubs;


    }
    public List<Subscriptions> subscriptionsbyvendor(long vendorid){
        List<Subscriptions> subscriptionsList = subscriptionRepository.findByvendorId(vendorid);
        if (subscriptionsList.isEmpty()) {
            return null;
        }
        else {
            return subscriptionsList;
        }


    }
















}
