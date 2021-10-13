package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.FollowUserRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.service.user.UserService;
import app.jg.og.zamong.service.user.follow.UserFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    private final UserFollowService userFollowService;
    private final SecurityContextService securityContextService;

    @GetMapping("/{user-uuid}")
    public ResponseBody userInformation(@PathVariable("user-uuid") String uuid) {
        return ResponseBody.of(userService.queryUserInformation(uuid), HttpStatus.OK.value());
    }

    @GetMapping("/me")
    public ResponseBody myInformation() {
        String uuid = securityContextService.getName();
        return ResponseBody.of(userService.queryUserInformation(uuid), HttpStatus.OK.value());
    }

    @PostMapping("/follow")
    public ResponseBody follow(@Valid @RequestBody FollowUserRequest request) {
        String userUuid = request.getUserUuid();
        String followerUuid = securityContextService.getName();
        return ResponseBody.of(userFollowService.followUser(userUuid, followerUuid), HttpStatus.OK.value());
    }
}
