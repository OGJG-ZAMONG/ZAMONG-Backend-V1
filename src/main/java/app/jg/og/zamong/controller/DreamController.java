package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamSleepDateTimeRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.ShareDreamService;
import app.jg.og.zamong.service.dream.find.DreamFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dream")
public class DreamController {

    private final DreamFindService dreamFindService;

    @GetMapping("/share")
    public ResponseBody share(@PathParam("page") int page, @PathParam("size") int size) {
        return ResponseBody.of(dreamFindService.queryShareDreams(page, size), HttpStatus.OK.value());
    }

    private final ShareDreamService shareDreamService;

    @PostMapping("/share")
    public ResponseBody share(@Valid @RequestBody ShareDreamRequest request) {
        return ResponseBody.of(shareDreamService.createShareDream(request), HttpStatus.CREATED.value());
    }

    @PutMapping("/share/{dream-uuid}")
    public ResponseBody share(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody ShareDreamRequest request) {
        return ResponseBody.of(shareDreamService.modifyShareDream(uuid, request), HttpStatus.OK.value());
    }

    @PatchMapping("/share/quality/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void quality(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody ShareDreamQualityRequest request) {
        shareDreamService.patchShareDreamQuality(uuid, request);
    }

    @PatchMapping("/share/sleep-datetime/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void sleepDateTime(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody ShareDreamSleepDateTimeRequest request) {
        shareDreamService.patchShareDreamSleepDateTime(uuid, request);
    }

    @PatchMapping("/title/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void title(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody DreamTitleRequest request) {
        shareDreamService.patchDreamTitle(uuid, request);
    }

    @PatchMapping("/content/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void content(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody DreamContentRequest request) {
        shareDreamService.patchDreamContent(uuid, request);
    }

    @PatchMapping("/dream-types/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void dreamTypes(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody DreamTypesRequest request) {
        shareDreamService.patchDreamTypes(uuid, request);
    }
}
