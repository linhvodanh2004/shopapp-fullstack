package com.project.shopapp.mappers;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.UserLoginDTO;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.UserLoginResponse;
import com.project.shopapp.responses.UserRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO userDTO);
    UserRegisterResponse register(User user);
}
