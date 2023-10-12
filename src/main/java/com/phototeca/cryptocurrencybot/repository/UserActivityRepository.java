package com.phototeca.cryptocurrencybot.repository;

import com.phototeca.cryptocurrencybot.domain.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long>, JpaSpecificationExecutor<UserActivity> {
}
