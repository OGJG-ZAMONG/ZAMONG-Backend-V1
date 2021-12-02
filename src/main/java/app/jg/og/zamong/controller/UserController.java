package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.ChangePasswordRequest;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.request.FollowUserRequest;
import app.jg.og.zamong.dto.request.ResetPasswordRequest;
import app.jg.og.zamong.dto.request.dream.LeaveUserRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.service.user.UserService;
import app.jg.og.zamong.service.user.find.UserFindService;
import app.jg.og.zamong.service.user.follow.UserFollowService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Validated
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    private final UserFindService userFindService;
    private final UserFollowService userFollowService;
    private final SecurityContextService securityContextService;

    @GetMapping("/{user-uuid}")
    public ResponseBody userInformation(@PathVariable("user-uuid") String uuid) {
        return ResponseBody.of(userFindService.queryUserInformation(uuid), HttpStatus.OK.value());
    }

    @GetMapping("/me")
    public ResponseBody myInformation() {
        return ResponseBody.of(userFindService.queryMyInformation(), HttpStatus.OK.value());
    }

    @PostMapping("/follow")
    public ResponseBody follow(@Valid @RequestBody FollowUserRequest request) {
        String userUuid = request.getUserUuid();
        String followerUuid = securityContextService.getName();
        return ResponseBody.of(userFollowService.followUser(userUuid, followerUuid), HttpStatus.OK.value());
    }

    @DeleteMapping("/follow")
    public ResponseBody followCancel(@Valid @RequestBody FollowUserRequest request) {
        return ResponseBody.of(userFollowService.cancelFollowUser(request), HttpStatus.OK.value());
    }

    @GetMapping("/{user-uuid}/following")
    public ResponseBody following(
            @PathVariable("user-uuid") String uuid,
            @RequestParam("page") int page,
            @Range(min = 1, max = 50) @RequestParam("size") int size) {
        return ResponseBody.of(userFollowService.queryFollowings(uuid, page, size), HttpStatus.OK.value());
    }

    @GetMapping("/{user-uuid}/follower")
    public ResponseBody follower(
            @PathVariable("user-uuid") String uuid,
            @RequestParam("page") int page,
            @Range(min = 1, max = 50) @RequestParam("size") int size) {
        return ResponseBody.listOf(userFollowService.queryFollowers(uuid, page, size), HttpStatus.OK.value());
    }

    @PatchMapping("/profile")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void profile(@RequestParam("file") MultipartFile file) {
        userService.modifyProfile(file);
    }

    @PatchMapping("/user-id")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void userId(@Valid @RequestBody CheckIdDuplicationRequest request) {
        userService.modifyUserId(request);
    }

    @PatchMapping("/password")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void password(@Valid @RequestBody ChangePasswordRequest request) {
        userService.modifyPassword(request);
    }

    @PostMapping("/password")
    public void password(@Valid @RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request);
    }

    @GetMapping("/password/find")
    public ResponseBody findPassword(@RequestParam("email") @NotNull @Email String email) {
        return ResponseBody.of(userService.sendFindPasswordEmail(email), HttpStatus.OK.value());
    }

    @DeleteMapping
    public void leave(@Valid @RequestBody LeaveUserRequest request) {
        userService.leaveUser(request);
    }

    @GetMapping("/search")
    public ResponseBody search(@RequestParam("query") @Size(min = 1, max = 50) String query) {
        return ResponseBody.listOf(userFindService.searchUsers(query), HttpStatus.OK.value());
    }
}
