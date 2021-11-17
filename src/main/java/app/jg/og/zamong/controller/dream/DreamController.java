package app.jg.og.zamong.controller.dream;

import app.jg.og.zamong.dto.request.DreamCommentRecommendRequest;
import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.dto.request.dream.DreamContentRequest;
import app.jg.og.zamong.dto.request.dream.DreamTitleRequest;
import app.jg.og.zamong.dto.request.dream.DreamTypesRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.DreamService;
import app.jg.og.zamong.service.dream.comment.DreamCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
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

    @PostMapping("/image/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void image(@PathVariable("dream-uuid") String uuid, @RequestParam("file") MultipartFile file) {
        dreamService.patchDreamImage(uuid, file);
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

    private final DreamCommentService dreamCommentService;

    @PostMapping("/{dream-uuid}/comment")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseBody comment(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody DreamCommentRequest request) {
        return ResponseBody.of(dreamCommentService.createDream(uuid, request), HttpStatus.CREATED.value());
    }

    @GetMapping("/{dream-uuid}/comment/count")
    public ResponseBody commentCount(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.of(dreamCommentService.queryCountOfComment(uuid), HttpStatus.OK.value());
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
