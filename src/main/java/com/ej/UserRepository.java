package com.ej;

import org.springframework.data.repository.CrudRepository;

import com.ej.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
