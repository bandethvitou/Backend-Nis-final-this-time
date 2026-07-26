package com.sarana_NIS.newTown_backend.service.core;

import com.sarana_NIS.newTown_backend.model.entity.core.User;

public interface UserService {
    User getUserById(Long userId);
    // Add other methods as needed
}