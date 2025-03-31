package com.Khaopiyoji.Khaopiyoji.Repository;

import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendors,Long> {
   Vendors findByvendorId(Long VendorId);
    void deleteByvendorId(Long vendorId);
    Vendors findByvendorusername(String vendorusername);
    void deleteByvendorusername(String vendorusername);
}
