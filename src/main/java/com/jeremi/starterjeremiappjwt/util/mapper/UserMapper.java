package com.jeremi.starterjeremiappjwt.util.mapper;

import com.jeremi.starterjeremiappjwt.model.User;
import com.jeremi.starterjeremiappjwt.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);

}
