package com.rentify.repository;

import com.rentify.model.StoreTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreThemeRepo extends JpaRepository<StoreTheme, Long>
{

}
