package com.application.backend.services;

import com.application.backend.entity.Result;

public interface AuthService {
    Result emailCaptcha(String emailUrl);
}
