package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public User register(@RequestBody SignUpUserRequest request) {
        return authService.registerUser(request);
    }
}
