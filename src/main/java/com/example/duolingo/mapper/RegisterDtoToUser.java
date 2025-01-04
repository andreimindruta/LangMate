package com.example.duolingo.mapper;

import com.example.duolingo.controller.payload.request.RegisterDto;
import com.example.duolingo.domain.entities.User;
import org.mapstruct.Mapper;

@Mapper
public interface RegisterDtoToUser {

    User registerDtoToUser(RegisterDto registerDto);
}
