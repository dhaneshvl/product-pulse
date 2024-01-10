package com.master.productpulse.mapper;

import com.master.productpulse.dto.UserLoginDTO;
import com.master.productpulse.model.User;

public class UserDtoMapper {

    public static UserLoginDTO mapToDTO(User user) {
        UserLoginDTO userLoginDto = new UserLoginDTO();
        userLoginDto.setId(user.getId());
        userLoginDto.setUsername(user.getUsername());
        userLoginDto.setEmail(user.getEmail());
        userLoginDto.setPhone(user.getPhone());
        userLoginDto.setName(user.getName());
        userLoginDto.setUserType(user.getUserType());
        userLoginDto.setAddedDate(user.getAddedDate());
        return userLoginDto;
    }
}
