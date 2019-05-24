package com.readingcontest.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.readingcontest.domain.UserCredentials;

@Repository
public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {

	Optional<UserCredentials> findByNameAndPassword(String username, String password);

}
