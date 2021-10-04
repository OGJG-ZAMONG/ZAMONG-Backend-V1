package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.CreateShareDreamRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.DreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dream")
public class DreamController {

    private final DreamService dreamService;

    @PostMapping("/share")
    public ResponseBody share(@Valid @RequestBody CreateShareDreamRequest request) {
        return ResponseBody.of(dreamService.createShareDream(request), HttpStatus.CREATED.value());
    }
}
