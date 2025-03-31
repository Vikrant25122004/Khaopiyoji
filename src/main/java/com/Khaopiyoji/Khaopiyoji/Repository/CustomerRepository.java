package com.Khaopiyoji.Khaopiyoji.Repository;

import com.Khaopiyoji.Khaopiyoji.Entity.Customer;
import com.Khaopiyoji.Khaopiyoji.Entity.Vendors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Customer findBycustomerId(Long customerId);
    Customer findBycustomerusername(String Customerusername);
    void deleteBycustomerusername(String Customerusername);

}


