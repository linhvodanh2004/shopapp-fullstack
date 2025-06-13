package com.project.pixelstore.services;

import com.project.pixelstore.dtos.UserDTO;
import com.project.pixelstore.models.User;

public interface UserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException, Exception;
    String login(String phoneNumber, String password) throws DataNotFoundException, Exception;
}
