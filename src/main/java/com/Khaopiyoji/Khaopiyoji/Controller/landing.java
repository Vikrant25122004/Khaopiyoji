package com.Khaopiyoji.Khaopiyoji.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class landing {
    @GetMapping
    public String landingpage(){
        return "Hi welcome to Our Tiffin Service platform Khaopiyoji, " +
                "if you want to become a well known vendor of Khaopiyoji than start with endpoint /public/create-vendor"+
                "if you are and suffering of daily unhealthy and unauthenticated food then start with /public/create-customer"+
                "login endpoint for customer /public/login-customer"+
                "for vendor /public/login-vendor"+
                "to check customer details /customer"+
                "to deactivate customer account /customer"+
                "to subscribe any vendor /customer/subscribe/{vendorusername}"+
                "to check customer subscription details /customer/subscription"+
                "to renew customer subscription /customer/renew/order"+
                "to get vendordetails /Vendor"+
                "to delete vendor /Vendor"+
                "to check all subscribed customer of vendor /Vendor/subscription";

    }
}
