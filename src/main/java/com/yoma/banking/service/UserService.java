package com.yoma.banking.service;

import com.yoma.banking.dto.UserDto;
import com.yoma.banking.model.User;

public interface UserService {
    void createUser(UserDto userDto);
    User findUserByEmail(String email);
}
