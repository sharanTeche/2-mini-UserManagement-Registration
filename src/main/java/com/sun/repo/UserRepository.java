package com.sun.repo;

import com.sun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByEmailAndPwd(String email, String pwd);
}
