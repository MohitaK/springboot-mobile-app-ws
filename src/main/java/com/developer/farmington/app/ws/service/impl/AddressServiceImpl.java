package com.developer.farmington.app.ws.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developer.farmington.app.ws.io.entity.AddressEntity;
import com.developer.farmington.app.ws.io.entity.UserEntity;
import com.developer.farmington.app.ws.io.repositories.AddressRepository;
import com.developer.farmington.app.ws.io.repositories.UserRepository;
import com.developer.farmington.app.ws.service.AddressService;
import com.developer.farmington.app.ws.shared.dto.AddressDto;
import com.developer.farmington.app.ws.ui.model.response.AddressRest;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AddressRepository addressRepository;

	@Override
	public List<AddressDto> getAllAddresses(String userId) {
		List<AddressDto> returnValue = new ArrayList<>();

		UserEntity userEntity = userRepository.findByUserId(userId);

		List<AddressEntity> addressList = addressRepository.findAllByUserDetails(userEntity);

//		ModelMapper modelMapper = new ModelMapper();
//		Type listType = new TypeToken<List<AddressDto>>() {}.getType();
//		returnValue = modelMapper.map(addressList, listType);

		for (AddressEntity addressEntity : addressList) {
			ModelMapper modelMapper = new ModelMapper();
			returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
		}

		return returnValue;
	}

}
