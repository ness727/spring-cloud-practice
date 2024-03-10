package com.megamaker.userservice.mapper;

import com.megamaker.userservice.dto.UserDto;
import com.megamaker.userservice.repository.UserEntity;
import com.megamaker.userservice.vo.RequestUser;
import com.megamaker.userservice.vo.ResponseUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(RequestUser requestUser);
    UserEntity toUserEntity(UserDto userDto);

    ResponseUser toResponseUser(UserDto userDto);
}
