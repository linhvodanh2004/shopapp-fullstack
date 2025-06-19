package com.project.pixelstore.services.impl;

import com.project.pixelstore.components.JwtTokenUtils;
import com.project.pixelstore.components.LocalizationUtils;
import com.project.pixelstore.constants.UserRole;
import com.project.pixelstore.dtos.UserDTO;
import com.project.pixelstore.mappers.UserMapper;
import com.project.pixelstore.models.Role;
import com.project.pixelstore.models.User;
import com.project.pixelstore.repositories.RoleRepository;
import com.project.pixelstore.repositories.UserRepository;
import com.project.pixelstore.services.UserService;
import com.project.pixelstore.utils.MessageKeys;
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
public class UserServiceImpl implements UserService {
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
        if (role.getName().toUpperCase().equals(UserRole.ADMIN)) {
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
