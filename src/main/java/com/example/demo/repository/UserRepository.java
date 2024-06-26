package com.example.demo.repository;

import com.example.demo.dto.BankRsponse;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);


    Optional<User> findByEmail(String email);

    Boolean existsByAcountNumber(String acountNumber);

    User findByAcountNumber(String acountNumber);



}
