package com.gracegh.springsecurityclient.Repository;

import com.gracegh.springsecurityclient.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
