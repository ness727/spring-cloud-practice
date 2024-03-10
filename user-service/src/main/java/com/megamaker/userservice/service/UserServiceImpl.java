package com.megamaker.userservice.service;

import com.megamaker.userservice.dto.UserDto;
import com.megamaker.userservice.mapper.UserMapper;
import com.megamaker.userservice.repository.UserEntity;
import com.megamaker.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setPwd(passwordEncoder.encode(userDto.getPwd()));

        UserEntity userEntity = UserMapper.INSTANCE.toUserEntity(userDto);
        userRepository.save(userEntity);
    }
}
