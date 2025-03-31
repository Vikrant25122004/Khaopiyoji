package com.Khaopiyoji.Khaopiyoji.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subscriptionid;

    @JoinColumn(name = "customerId")
    private long customerId;

    @JoinColumn(name = "vendorId")
    private long vendorId;


    @JoinColumn(name = "customerusername")
    private String customerusername;

    @JoinColumn(name = "vendorusername")
    private String vendorusername;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    private boolean isActive;

}
