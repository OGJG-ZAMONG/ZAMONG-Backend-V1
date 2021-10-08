package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.ShareDreamRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.DreamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dream")
public class DreamController {

    private final DreamService dreamService;

    @PostMapping("/share")
    public ResponseBody share(@Valid @RequestBody ShareDreamRequest request) {
        return ResponseBody.of(dreamService.createShareDream(request), HttpStatus.CREATED.value());
    }

    @PutMapping("/share/{dream-uuid}")
    public ResponseBody share(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody ShareDreamRequest request) {
        return ResponseBody.of(dreamService.modifyShareDream(uuid, request), HttpStatus.OK.value());
    }
}
