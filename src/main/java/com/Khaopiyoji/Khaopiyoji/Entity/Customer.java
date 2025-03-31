package com.Khaopiyoji.Khaopiyoji.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(nullable = false , unique = true)
    private String customerusername;
    @Column(nullable = false)
    private String Customername;
    private String email;
    private String password;
    private String address;

    @Column(name = "subscription_vendor_username")
    private String subscriptionVendorUsername;

    @Column(name = "subscriptionid")
    private long subscriptionid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date StartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date EndDate;

    @Column(name = "is_active")
    private Boolean subscriptionIsActive;

}
