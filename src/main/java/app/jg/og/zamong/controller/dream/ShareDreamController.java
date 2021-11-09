package app.jg.og.zamong.controller.dream;

import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamSleepDateTimeRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.share.ShareDreamService;
import app.jg.og.zamong.service.dream.share.find.ShareDreamFindService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.websocket.server.PathParam;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dream/share")
public class ShareDreamController {

    private final ShareDreamFindService shareDreamFindService;

    @GetMapping
    public ResponseBody share(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size,
            @PathParam("sort") String sort) {
        return ResponseBody.listOf(shareDreamFindService.queryShareDreams(page, size, sort), HttpStatus.OK.value());
    }

    @GetMapping("/me")
    public ResponseBody myShareDream(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size,
            @PathParam("sort") String sort) {
        return ResponseBody.listOf(shareDreamFindService.queryMyShareDreams(page, size, sort), HttpStatus.OK.value());
    }

    @GetMapping("/me/today")
    public ResponseBody todayMyShareDream() {
        return ResponseBody.listOf(shareDreamFindService.queryTodayMyShareDreams(), HttpStatus.OK.value());
    }

    @GetMapping("/{dream-uuid}")
    public ResponseBody shareDreamInformation(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.of(shareDreamFindService.queryShareDreamInformation(uuid), HttpStatus.OK.value());
    }

    @GetMapping("/timetable")
    public ResponseBody shareDreamTimeTable(
            @Range(min = 2000, max = 3000) @PathParam("year") int year,
            @Range(min = 1, max = 12) @PathParam("month") int month
    ) {
        return ResponseBody.listOf(shareDreamFindService.queryMyShareDreamTimeTable(year, month), HttpStatus.OK.value());
    }

    @GetMapping("/timetable/v2")
    public ResponseBody shareDreamTimeTableV2(
            @Range(min = 2000, max = 3000) @PathParam("year") int year,
            @Range(min = 1, max = 12) @PathParam("month") int month
    ) {
        return ResponseBody.listOf(shareDreamFindService.queryShareDreamTimeTableV2(year, month), HttpStatus.OK.value());
    }

    @GetMapping("/follow")
    public ResponseBody follwoing(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size
    ) {
        return ResponseBody.listOf(shareDreamFindService.queryFollowShareDreams(page, size), HttpStatus.OK.value());
    }

    private final ShareDreamService shareDreamService;

    @PostMapping
    public ResponseBody share(@Valid @RequestBody ShareDreamRequest request) {
        return ResponseBody.of(shareDreamService.createShareDream(request), HttpStatus.CREATED.value());
    }

    @PostMapping("/{dream-uuid}")
    public ResponseBody share(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.of(shareDreamService.doShareDream(uuid), HttpStatus.OK.value());
    }

    @PutMapping("/{dream-uuid}")
    public ResponseBody share(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody ShareDreamRequest request) {
        return ResponseBody.of(shareDreamService.modifyShareDream(uuid, request), HttpStatus.OK.value());
    }

    @PostMapping("/image/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void image(@PathVariable("dream-uuid") String uuid, @RequestParam("file") MultipartFile file) {
        shareDreamService.patchShareDreamImage(uuid, file);
    }

    @PatchMapping("/quality/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void quality(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody ShareDreamQualityRequest request) {
        shareDreamService.patchShareDreamQuality(uuid, request);
    }

    @PatchMapping("/sleep-datetime/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void sleepDateTime(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody ShareDreamSleepDateTimeRequest request) {
        shareDreamService.patchShareDreamSleepDateTime(uuid, request);
    }

    @PostMapping("/{dream-uuid}/lucy")
    public void lucy(@PathVariable("dream-uuid") String uuid) {
        shareDreamService.addShareDreamLucy(uuid);
    }

    @DeleteMapping("/{dream-uuid}/lucy")
    public void lucyCancel(@PathVariable("dream-uuid") String uuid) {
        shareDreamService.cancelShareDreamLucy(uuid);
    }
}
