package com.imaginarynil.broadcast_server.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Query(value = "SELECT * FROM user WHERE username = :username",nativeQuery = true)
    public Optional<User> findByUsername(String username);
}
