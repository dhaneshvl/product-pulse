package com.master.productpulse.service;

import com.master.productpulse.dto.UserLoginDTO;
import com.master.productpulse.model.User;
import com.master.productpulse.repository.UserRepository;
import com.master.productpulse.request.LoginRequest;
import com.master.productpulse.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserLoginService {

    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public ApiResponse<?> login(LoginRequest loginRequest, HttpServletRequest httpServletRequest,String traceID) throws Exception{
            Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
            if (userOptional.isPresent()) {
                if (userOptional.get().getPassword().equals(loginRequest.getPassword())) {
                    return new ApiResponse<>(true, "Login success", mapToDTO(userOptional.get()), LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.ACCEPTED);
                } else {
                    return new ApiResponse<>(false, "Incorrect password", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.ACCEPTED);
                }
            } else {
                return new ApiResponse<>(false, "Invalid username", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.ACCEPTED);
            }
    }

    public static UserLoginDTO mapToDTO(User user) {
        UserLoginDTO userLoginDto = new UserLoginDTO();
        userLoginDto.setId(user.getId());
        userLoginDto.setUsername(user.getUsername());
        userLoginDto.setEmail(user.getEmail());
        userLoginDto.setPhone(user.getPhone());
        userLoginDto.setName(user.getName());
        userLoginDto.setUserType(user.getUserType());
        return userLoginDto;
    }
}
