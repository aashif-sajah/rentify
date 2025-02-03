package com.rentify.repository;

import com.rentify.model.Business;
import com.rentify.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepo extends JpaRepository<Business, Long> {
    Optional<Business> findByOwner(Users owner);
    Optional<Business> findByStoreSlug(String storeSlug);
}
