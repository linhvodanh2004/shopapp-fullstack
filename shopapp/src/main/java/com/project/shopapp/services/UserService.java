package com.project.shopapp.services;

import com.project.shopapp.components.JwtTokenUtils;
import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exceptions.*;
import com.project.shopapp.mappers.UserMapper;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final LocalizationUtils localizationUtils;
    private final UserMapper userMapper;

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();
        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            throw new Exception(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCHED));
        }
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException(localizationUtils.getLocalizedMessage(MessageKeys.PHONE_EXISTED));
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(localizationUtils
                        .getLocalizedMessage(MessageKeys.ROLE_NOT_FOUND)));
        if (role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDeniedException(localizationUtils.getLocalizedMessage(MessageKeys.CREATE_ADMIN_FAILED));
        }
        User newUser = userMapper.toUser(userDTO);
        newUser.setRole(role);
        newUser.setActive(true);
        // password is not required if fb or gg account is available
        if (userDTO.getGoogleAccountId() == 0 && userDTO.getFacebookAccountId() == 0) {
            // encoding password before saving in database
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isEmpty()) {
            throw new DataNotFoundException(localizationUtils
                    .getLocalizedMessage(MessageKeys.INVALID_PHONE_OR_PASSWORD));
        }
        if (user.get().getGoogleAccountId() == 0 && user.get().getFacebookAccountId() == 0) {
            if (!passwordEncoder.matches(password, user.get().getPassword())) {
                throw new BadCredentialsException(localizationUtils
                        .getLocalizedMessage(MessageKeys.INVALID_PHONE_OR_PASSWORD));
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(phoneNumber, password);
        // Authenticate with Spring Security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateTokenFromUser(user.get());
    }
}
