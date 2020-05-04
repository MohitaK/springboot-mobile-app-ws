package com.developer.farmington.app.ws.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.developer.farmington.app.ws.exceptions.UserServiceException;
import com.developer.farmington.app.ws.service.AddressService;
import com.developer.farmington.app.ws.service.UserService;
import com.developer.farmington.app.ws.shared.dto.AddressDto;
import com.developer.farmington.app.ws.shared.dto.UserDto;
import com.developer.farmington.app.ws.ui.model.request.UserDetailsRequestModel;
import com.developer.farmington.app.ws.ui.model.response.AddressRest;
import com.developer.farmington.app.ws.ui.model.response.ErrorMessages;
import com.developer.farmington.app.ws.ui.model.response.OperationStatusModel;
import com.developer.farmington.app.ws.ui.model.response.RequestOperationName;
import com.developer.farmington.app.ws.ui.model.response.RequestOperationStatus;
import com.developer.farmington.app.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users") // http://localhost:8080/users
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	AddressService addressService;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {

		UserDto getUserId = userService.getUserByUserId(id);

//		UserRest returnValue = new UserRest();
//		BeanUtils.copyProperties(getUserId, returnValue);

		ModelMapper modelMapper = new ModelMapper();
		UserRest returnValue = modelMapper.map(getUserId, UserRest.class);

		return returnValue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
		UserRest returnValue = new UserRest();

		if (userDetails.getLastName().isEmpty()) {
//			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getMessage());
			throw new NullPointerException("Null Object");
		}

//		UserDto userDto = new UserDto();
//		BeanUtils.copyProperties(userDetails, userDto);
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);

		UserDto createUser = userService.createUser(userDto);
		returnValue = modelMapper.map(createUser, UserRest.class);
//		BeanUtils.copyProperties(createUser, returnValue);

		return returnValue;
	}

	@PutMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = new UserRest();

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);

		UserDto updateUser = userService.updateUser(id, userDto);
		BeanUtils.copyProperties(updateUser, returnValue);

		return returnValue;
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();

		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(id);

		returnValue.setOperationStatus(RequestOperationStatus.SUCCESS.name());

		return returnValue;
	}

	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "25") int limit) {
		List<UserRest> returnValue = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit);

		for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}

		return returnValue;
	}

	@GetMapping(path = "/{id}/addresses", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public List<AddressRest> getAddresses(@PathVariable String id) {
		List<AddressRest> returnValue = new ArrayList<>();

		List<AddressDto> addressList = addressService.getAllAddresses(id);

		if (addressList != null && !addressList.isEmpty()) {
			ModelMapper modelMapper = new ModelMapper();
			Type listType = new TypeToken<List<AddressRest>>() {
			}.getType();
			returnValue = modelMapper.map(addressList, listType);
		}

		return returnValue;
	}

}
