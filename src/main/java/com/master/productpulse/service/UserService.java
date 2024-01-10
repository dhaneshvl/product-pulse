package com.master.productpulse.service;

import com.master.productpulse.dto.UserLoginDTO;
import com.master.productpulse.exception.UserNotFoundException;
import com.master.productpulse.model.User;
import com.master.productpulse.repository.UserRepository;
import com.master.productpulse.request.UserSignUpRequest;
import com.master.productpulse.utils.ApiResponse;
import com.master.productpulse.utils.PasswordGenerator;
import jakarta.servlet.http.HttpServletRequest;
import com.master.productpulse.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public ApiResponse<?> addUser(UserSignUpRequest userSignUpRequest, HttpServletRequest httpServletRequest, String traceID) throws Exception{
        try {
            if (isUsernameTaken(userSignUpRequest.getUsername()))
                return new ApiResponse<>(false, "Sorry, This username has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CONFLICT);

            if (isEmailTaken(userSignUpRequest.getEmail()))
                return new ApiResponse<>(false, "Sorry, This email has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CONFLICT);

            if (isPhoneTaken(userSignUpRequest.getPhone()))
                return new ApiResponse<>(false, "Sorry, This phone number has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CREATED);

            boolean firstLogin = true;

            String randomPassword = userSignUpRequest.getPassword();

            if(randomPassword==null||randomPassword.isEmpty()) {
                 randomPassword = PasswordGenerator.generateRandomPassword();
            }

            User newUser = userRepository.save(new User(userSignUpRequest.getName(), userSignUpRequest.getUsername(), userSignUpRequest.getEmail(), userSignUpRequest.getPhone(), randomPassword, firstLogin, LocalDateTime.now(), userSignUpRequest.getUserType(),LocalDateTime.now()));

            return new ApiResponse<>(true, "User added successfully", UserDtoMapper.mapToDTO(newUser), LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.ACCEPTED);

        } catch (Exception e) {
            e.printStackTrace(); // Example: Print the stack trace
            return new ApiResponse<>(false, "Unable to process your request at the moment. Trace ID: " + traceID, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ApiResponse<?> updateUser(Long userId, UserSignUpRequest userSignUpRequest, HttpServletRequest httpServletRequest, String traceID) throws Exception {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getEmail().equals(userSignUpRequest.getEmail()) && isEmailTaken(userSignUpRequest.getEmail())) {
            return new ApiResponse<>(false, "Sorry, This email has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CONFLICT);
        }

        if (!user.getUsername().equals(userSignUpRequest.getUsername()) && isUsernameTaken(userSignUpRequest.getUsername()))
            return new ApiResponse<>(false, "Sorry, This username has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CONFLICT);

        if (!user.getPhone().equals(userSignUpRequest.getPhone()) && isPhoneTaken(userSignUpRequest.getPhone()))
            return new ApiResponse<>(false, "Sorry, This phone number has been taken", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CREATED);

        // Update the store details
        user.setPhone(userSignUpRequest.getPhone());
        user.setName(userSignUpRequest.getName());
        user.setUserType(userSignUpRequest.getUserType());
        user.setEmail(userSignUpRequest.getEmail());
        user.setUsername(userSignUpRequest.getUsername());

        // Save the updated store
        userRepository.save(user);

        user = null;
        return new ApiResponse<>(true, "User updated successfully", null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.CREATED);
    }

    public ApiResponse<?> getAllUsers(HttpServletRequest httpServletRequest, String traceID) {
        List<UserLoginDTO> userList = userRepository.findAll()
                .stream()
                .map(UserDtoMapper::mapToDTO)
                .sorted(Comparator.comparing(UserLoginDTO::getName)).collect(Collectors.toList());
        return new ApiResponse<>(true, "Success", userList, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    public ApiResponse<?> getUser(Long userId, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new ApiResponse<>(true, "success", UserDtoMapper.mapToDTO(user), LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    public ApiResponse<?> deleteUser(Long userId, HttpServletRequest httpServletRequest, String traceID) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
        return new ApiResponse<>(true, String.format("User, [%s] has been successfully deleted.", user.getName()), null, LocalDateTime.now(), httpServletRequest.getRequestURI(), HttpStatus.OK);

    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isPhoneTaken(String phone) {
        return userRepository.findByPhone(phone).isPresent();
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
