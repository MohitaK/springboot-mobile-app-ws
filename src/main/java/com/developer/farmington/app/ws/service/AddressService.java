package com.developer.farmington.app.ws.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.developer.farmington.app.ws.shared.dto.AddressDto;

@Service
public interface AddressService {
	List<AddressDto> getAllAddresses(String userId);
}
