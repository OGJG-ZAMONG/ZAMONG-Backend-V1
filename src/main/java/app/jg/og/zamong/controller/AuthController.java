package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.EmailAuthenticationRequest;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.dto.response.SignedUserResponse;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public SignedUserResponse register(@Valid @RequestBody SignUpUserRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public IssueTokenResponse login(@Valid @RequestBody LoginUserRequest request) {
        return authService.loginUser(request);
    }

    @PostMapping("/mail")
    public void mail(@Valid @RequestBody EmailAuthenticationRequest request) {
        authService.sendOutAuthenticationEmail(request);
    }
}
