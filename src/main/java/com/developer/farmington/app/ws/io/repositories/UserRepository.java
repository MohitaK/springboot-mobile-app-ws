package com.developer.farmington.app.ws.io.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.developer.farmington.app.ws.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
//	UserEntity findUserByEmail(String email);
	UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);
}
