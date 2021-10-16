package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.FollowUserRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.service.user.UserService;
import app.jg.og.zamong.service.user.follow.UserFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

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
        return ResponseBody.of(userService.queryMyInformation(), HttpStatus.OK.value());
    }

    @PostMapping("/follow")
    public ResponseBody follow(@Valid @RequestBody FollowUserRequest request) {
        String userUuid = request.getUserUuid();
        String followerUuid = securityContextService.getName();
        return ResponseBody.of(userFollowService.followUser(userUuid, followerUuid), HttpStatus.OK.value());
    }

    @GetMapping("/{user-uuid}/following")
    public ResponseBody following(
            @PathVariable("user-uuid") String uuid,
            @PathParam("page") int page,
            @PathParam("size") int size) {
        return ResponseBody.of(userFollowService.queryFollowings(uuid, page, size), HttpStatus.OK.value());
    }

    @GetMapping("/{user-uuid}/follower")
    public ResponseBody follower(
            @PathVariable("user-uuid") String uuid,
            @PathParam("page") int page,
            @PathParam("size") int size) {
        return ResponseBody.listOf(userFollowService.queryFollowers(uuid, page, size), HttpStatus.OK.value());
    }
}
