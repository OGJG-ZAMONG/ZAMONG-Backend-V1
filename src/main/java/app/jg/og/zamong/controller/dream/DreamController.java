package app.jg.og.zamong.controller.dream;

import app.jg.og.zamong.dto.request.DreamCommentRecommendRequest;
import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import app.jg.og.zamong.dto.request.dream.interpretationdream.InterpretationDreamRequest;
import app.jg.og.zamong.dto.request.dream.selldream.SellDreamCostRequest;
import app.jg.og.zamong.dto.request.dream.selldream.SellDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamQualityRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamRequest;
import app.jg.og.zamong.dto.request.dream.sharedream.ShareDreamSleepDateTimeRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.DreamService;
import app.jg.og.zamong.service.dream.comment.DreamCommentService;
import app.jg.og.zamong.service.dream.interpretation.InterpretationDreamService;
import app.jg.og.zamong.service.dream.sell.SellDreamService;
import app.jg.og.zamong.service.dream.sell.find.SellDreamFindService;
import app.jg.og.zamong.service.dream.share.ShareDreamService;
import app.jg.og.zamong.service.dream.share.find.ShareDreamFindService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @Max(50) @PathParam("size") int size,
            @PathParam("sort") String sort) {
        return ResponseBody.listOf(shareDreamFindService.queryShareDreams(page, size, sort), HttpStatus.OK.value());
    }

    @GetMapping("/share/me")
    public ResponseBody myShareDream(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size,
            @PathParam("sort") String sort) {
        return ResponseBody.listOf(shareDreamFindService.queryMyShareDreams(page, size, sort), HttpStatus.OK.value());
    }

    @GetMapping("/share/me/today")
    public ResponseBody todayMyShareDream() {
        return ResponseBody.listOf(shareDreamFindService.queryTodayMyShareDreams(), HttpStatus.OK.value());
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
        return ResponseBody.listOf(shareDreamFindService.queryMyShareDreamTimeTable(year, month), HttpStatus.OK.value());
    }

    @GetMapping("/share/timetable/v2")
    public ResponseBody shareDreamTimeTableV2(
            @Range(min = 2000, max = 3000) @PathParam("year") int year,
            @Range(min = 1, max = 12) @PathParam("month") int month
    ) {
        return ResponseBody.listOf(shareDreamFindService.queryShareDreamTimeTableV2(year, month), HttpStatus.OK.value());
    }

    @GetMapping("/share/follow")
    public ResponseBody follwoing(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size
    ) {
        return ResponseBody.listOf(shareDreamFindService.queryFollowShareDreams(page, size), HttpStatus.OK.value());
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

    @PostMapping("/share/image/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void image(@PathVariable("dream-uuid") String uuid, @RequestParam("file")MultipartFile file) {
        shareDreamService.patchShareDreamImage(uuid, file);
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

    @PostMapping("/share/{dream-uuid}/lucy")
    public void lucy(@PathVariable("dream-uuid") String uuid) {
        shareDreamService.addShareDreamLucy(uuid);
    }

    @DeleteMapping("/share/{dream-uuid}/lucy")
    public void lucyCancel(@PathVariable("dream-uuid") String uuid) {
        shareDreamService.cancelShareDreamLucy(uuid);
    }

    private final SellDreamService sellDreamService;

    @PostMapping("/sell")
    public ResponseBody sell(@Valid @RequestBody SellDreamRequest request) {
        return ResponseBody.of(sellDreamService.createSellDream(request), HttpStatus.CREATED.value());
    }

    @PostMapping("/sell/{dream-uuid}/done")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void sellDone(@PathVariable("dream-uuid") String uuid) {
        sellDreamService.doneSellDream(uuid);
    }

    @DeleteMapping("/sell/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void sellCancel(@PathVariable("dream-uuid") String uuid) {
        sellDreamService.cancelSellDream(uuid);
    }

    @PatchMapping("/sell/cost/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void cost(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody SellDreamCostRequest request) {
        sellDreamService.patchSellDreamCost(uuid, request);
    }

    @PostMapping("/sell/{dream-uuid}/request")
    public ResponseBody sellRequest(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.of(sellDreamService.doSellRequestDream(uuid), HttpStatus.OK.value());
    }

    private final SellDreamFindService sellDreamFindService;

    @GetMapping("/sell/continue")
    public ResponseBody sellContinue(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size
    ) {
        return ResponseBody.listOf(sellDreamFindService.queryPendingSellDreams(page, size), HttpStatus.OK.value());
    }

    @GetMapping("/sell/close")
    public ResponseBody sellClose(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size
    ) {
        return ResponseBody.listOf(sellDreamFindService.queryClosedSellDream(page, size), HttpStatus.OK.value());
    }

    private final InterpretationDreamService interpretationDreamService;

    @PostMapping("/interpretation")
    public ResponseBody interpretation(@Valid @RequestBody InterpretationDreamRequest request) {
        return ResponseBody.of(interpretationDreamService.createInterpretationDream(request), HttpStatus.CREATED.value());
    }

    @GetMapping("/interpretation/static")
    public ResponseBody interpretationDream() {
        return ResponseBody.listOf(interpretationDreamService.queryInterpretationDreamCategory(), HttpStatus.OK.value());
    }

    @GetMapping("/interpretation/static/{interpretation-uuid}")
    public ResponseBody interpretationDetail(@PathVariable("interpretation-uuid") String uuid) {
        return ResponseBody.of(interpretationDreamService.queryInterpretation(uuid), HttpStatus.OK.value());
    }

    private final DreamCommentService dreamCommentService;

    @PostMapping("/{dream-uuid}/comment")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseBody comment(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody DreamCommentRequest request) {
        return ResponseBody.of(dreamCommentService.createDream(uuid, request), HttpStatus.CREATED.value());
    }

    @GetMapping("/{dream-uuid}/comment")
    public ResponseBody comment(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.listOf(dreamCommentService.queryDreamComment(uuid), HttpStatus.OK.value());
    }

    @GetMapping("/comment/{comment-uuid}/comment")
    public ResponseBody reComment(@PathVariable("comment-uuid") String uuid) {
        return ResponseBody.listOf(dreamCommentService.queryDreamReComment(uuid), HttpStatus.OK.value());
    }

    @PostMapping("/comment/{comment-uuid}/recommend")
    @ResponseStatus(HttpStatus.CREATED)
    public void recommend(@PathVariable("comment-uuid") String uuid, @Valid @RequestBody DreamCommentRecommendRequest request) {
        dreamCommentService.doCommentRecommend(uuid, request);
    }

    @PatchMapping("/comment/{comment-uuid}/content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void content(@PathVariable("comment-uuid") String uuid, @Valid @RequestBody DreamCommentRequest request) {
        dreamCommentService.patchCommentContent(uuid, request);
    }

    @PostMapping("/comment/{comment-uuid}/check")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void check(@PathVariable("comment-uuid") String uuid) {
        dreamCommentService.checkDreamComment(uuid);
    }
}
