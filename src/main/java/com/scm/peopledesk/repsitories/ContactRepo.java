package com.scm.peopledesk.repsitories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scm.peopledesk.entities.Contact;
import com.scm.peopledesk.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact,String>{
      //find the contacts by user

      //custom finder method
      List<Contact> findByUser(User user);

      //custom query method to get all contacts of a user
      @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
      List<Contact> findByUserId(String userId);
}
