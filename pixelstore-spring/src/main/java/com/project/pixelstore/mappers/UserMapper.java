package com.project.pixelstore.mappers;

import com.project.pixelstore.dtos.UserDTO;
import com.project.pixelstore.models.User;
import com.project.pixelstore.responses.UserRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDTO userDTO);
    UserRegisterResponse register(User user);
}
