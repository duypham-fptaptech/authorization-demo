package com.example.demo_authorization.repository;

import com.example.demo_authorization.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization,String> {
}
