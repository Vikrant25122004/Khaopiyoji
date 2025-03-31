package com.Khaopiyoji.Khaopiyoji.Entity;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Vendors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;
    @Column(nullable = false , unique = true)
    private String vendorusername;
    private String Vendorname;
    private String email;
    private String password;
    private String BusinessArea;
    private String Lunchtime;
    private String breakfast;
    private String Dinner;
    private String Speciality;
    private List<String> addons;
    private String subscription_price;
    @OneToMany
    private List<Subscriptions> subscriptions;




}
