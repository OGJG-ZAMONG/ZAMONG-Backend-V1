package app.jg.og.zamong.controller;

import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamSleepDateTimeRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.DreamService;
import app.jg.og.zamong.service.dream.comment.DreamCommentService;
import app.jg.og.zamong.service.dream.share.ShareDreamService;
import app.jg.og.zamong.service.dream.find.ShareDreamFindService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.websocket.server.PathParam;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/dream")
public class DreamController {

    private final DreamService dreamService;

    @PatchMapping("/title/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void title(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody DreamTitleRequest request) {
        dreamService.patchDreamTitle(uuid, request);
    }

    @PatchMapping("/content/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void content(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody DreamContentRequest request) {
        dreamService.patchDreamContent(uuid, request);
    }

    @PatchMapping("/dream-types/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void dreamTypes(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody DreamTypesRequest request) {
        dreamService.patchDreamTypes(uuid, request);
    }

    @DeleteMapping("/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("dream-uuid") String uuid) {
        dreamService.removeDream(uuid);
    }

    private final ShareDreamFindService shareDreamFindService;

    @GetMapping("/share")
    public ResponseBody share(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size) {
        return ResponseBody.listOf(shareDreamFindService.queryShareDreams(page, size), HttpStatus.OK.value());
    }

    @GetMapping("/share/me")
    public ResponseBody myShareDream(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size) {
        return ResponseBody.listOf(shareDreamFindService.queryMyShareDreams(page, size), HttpStatus.OK.value());
    }

    @GetMapping("/share/{dream-uuid}")
    public ResponseBody shareDreamInformation(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.of(shareDreamFindService.queryShareDreamInformation(uuid), HttpStatus.OK.value());
    }

    @GetMapping("/share/timetable")
    public ResponseBody shareDreamTimeTable(
            @Range(min = 2000, max = 3000) @PathParam("year") int year,
            @Range(min = 1, max = 12) @PathParam("month") int month
    ) {
        return ResponseBody.of(shareDreamFindService.queryMyShareDreamTimeTable(year, month), HttpStatus.OK.value());
    }

    @GetMapping("/share/timetable/v2")
    public ResponseBody shareDreamTimeTableV2(
            @Range(min = 2000, max = 3000) @PathParam("year") int year,
            @Range(min = 1, max = 12) @PathParam("month") int month
    ) {
        return ResponseBody.of(shareDreamFindService.queryShareDreamTimeTableV2(year, month), HttpStatus.OK.value());
    }

    private final ShareDreamService shareDreamService;

    @PostMapping("/share")
    public ResponseBody share(@Valid @RequestBody ShareDreamRequest request) {
        return ResponseBody.of(shareDreamService.createShareDream(request), HttpStatus.CREATED.value());
    }

    @PostMapping("/share/{dream-uuid}")
    public ResponseBody share(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.of(shareDreamService.doShareDream(uuid), HttpStatus.OK.value());
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

    private final DreamCommentService dreamCommentService;

    @PostMapping("/{dream-uuid}/comment")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseBody comment(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody DreamCommentRequest request) {
        return ResponseBody.of(dreamCommentService.createDream(uuid, request), HttpStatus.CREATED.value());
    }
}
