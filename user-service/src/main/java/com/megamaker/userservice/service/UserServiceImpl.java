package com.megamaker.userservice.service;

import com.megamaker.userservice.mapper.UserMapper;
import com.megamaker.userservice.repository.UserEntity;
import com.megamaker.userservice.repository.UserRepository;
import com.megamaker.userservice.vo.ResponseOrder;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new NoResultException("User not found");
        }

        UserDto userDto = UserMapper.INSTANCE.toUserDto(userEntity);
        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(userEntity.getEmail(), userEntity.getPwd(),
                true, true, true, true,
                Set.of(new SimpleGrantedAuthority("User")));
    }
}
