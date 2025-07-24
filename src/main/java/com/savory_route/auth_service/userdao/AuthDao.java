package com.savory_route.auth_service.userdao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.savory_route.auth_service.usermodel.AuthModel;

public interface AuthDao extends MongoRepository<AuthModel, String>{

	Optional<AuthModel> findByEmail(String email);

	Optional<AuthModel> findByPhoneNumber(long phoneNumber);
}
