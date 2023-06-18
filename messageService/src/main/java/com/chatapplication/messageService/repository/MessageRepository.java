package com.chatapplication.messageService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatapplication.messageService.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

}
