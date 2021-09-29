package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/{user-uuid}")
    public ResponseBody userInformation(@PathVariable("user-uuid") String uuid) {
        return ResponseBody.of(userService.queryUserInformation(uuid), HttpStatus.OK.value());
    }
}
