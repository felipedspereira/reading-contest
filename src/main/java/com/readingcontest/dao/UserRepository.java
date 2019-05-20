package com.readingcontest.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.readingcontest.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	Optional<User> findByName(String name);

}
