package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.*;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseBody register(@Valid @RequestBody SignUpUserRequest request) {
        return ResponseBody.of(authService.registerUser(request), HttpStatus.CREATED.value());
    }

    @PostMapping("/login")
    public ResponseBody login(@Valid @RequestBody LoginUserRequest request) {
        return ResponseBody.of(authService.loginUser(request), HttpStatus.OK.value());
    }

    @PostMapping("/user-id/duplicate")
    public ResponseBody duplicate(@Valid @RequestBody CheckIdDuplicationRequest request) {
        return ResponseBody.of(authService.checkIdDuplication(request), HttpStatus.OK.value());
    }

    @PostMapping("/mail")
    public ResponseBody mail(@Valid @RequestBody EmailAuthenticationRequest request) {
        return ResponseBody.of(authService.sendOutAuthenticationEmail(request), HttpStatus.OK.value());
    }

    @PostMapping("/refresh")
    public ResponseBody refresh(@Valid @RequestBody ReIssueTokenRequest request) {
        return ResponseBody.of(authService.refreshToken(request), HttpStatus.OK.value());
    }
}
