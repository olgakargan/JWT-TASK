package com.example.jwttask.repository;



import com.example.jwttask.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM message ORDER BY ID DESC LIMIT ?1", nativeQuery = true)
    List<Message> findLastMessages(int count);
}