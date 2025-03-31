package com.Khaopiyoji.Khaopiyoji.Repository;

import com.Khaopiyoji.Khaopiyoji.Entity.Subscriptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscriptions,Long> {
    Subscriptions findBycustomerId(long customerid);
    List<Subscriptions> findByvendorId(long VendorId);
    Subscriptions findBysubscriptionid(long subscriptionid);
}
