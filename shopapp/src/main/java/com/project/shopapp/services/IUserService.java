package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.*;
import com.project.shopapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException, Exception;
    String login(String phoneNumber, String password) throws DataNotFoundException, Exception;
}
