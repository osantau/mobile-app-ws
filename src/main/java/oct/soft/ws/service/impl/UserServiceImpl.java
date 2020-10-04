package oct.soft.ws.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oct.soft.ws.io.entity.UserEntity;
import oct.soft.ws.repository.UserRepository;
import oct.soft.ws.service.UserService;
import oct.soft.ws.ui.shared.Utils;
import oct.soft.ws.ui.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Utils utils;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		
		if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");		
				
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);		
		userEntity.setEncryptedPassword("test");
		userEntity.setUserId(utils.generateUserId(50));
		
		UserEntity storedUserDetails = userRepository.save(userEntity);
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		
		return returnValue;
	}

}
