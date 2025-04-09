package com.Khaopiyoji.Khaopiyoji.Repository;

import com.Khaopiyoji.Khaopiyoji.Entity.Customer;
import com.Khaopiyoji.Khaopiyoji.Entity.myorder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface myorderrepository extends JpaRepository<myorder, Long> {
    myorder findByMyOrderid(Long myOrderid);

}
