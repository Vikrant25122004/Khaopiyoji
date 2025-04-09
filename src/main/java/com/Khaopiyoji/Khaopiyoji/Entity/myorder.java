package com.Khaopiyoji.Khaopiyoji.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class myorder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myOrderid;
    private String status;
    private String orderid;
    private String reciept;
    private String customerusername;
    private String vendorusername;
    private String paymentId;
    private int amount;
}
