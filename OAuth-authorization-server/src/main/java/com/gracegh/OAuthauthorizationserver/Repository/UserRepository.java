package com.gracegh.OAuthauthorizationserver.Repository;

import com.gracegh.OAuthauthorizationserver.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
