package app.jg.og.zamong.controller.dream;

import app.jg.og.zamong.dto.request.dream.interpretationdream.InterpretationDreamRequest;
import app.jg.og.zamong.dto.request.dream.interpretationdream.SelectInterpretationDreamRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.interpretation.InterpretationDreamService;
import app.jg.og.zamong.service.dream.interpretation.find.InterpretationDreamFindService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dream/interpretation")
public class InterpretationDreamController {

    private final InterpretationDreamService interpretationDreamService;
    private final InterpretationDreamFindService interpretationDreamFindService;

    @GetMapping
    public ResponseBody interpretation(
            @RequestParam("page") int page,
            @Range(min = 1, max = 50) @RequestParam("size") int size
    ) {
        return ResponseBody.listOf(interpretationDreamFindService.queryInterpretationDreams(page, size), HttpStatus.OK.value());
    }

    @PostMapping
    public ResponseBody interpretation(@Valid @RequestBody InterpretationDreamRequest request) {
        return ResponseBody.of(interpretationDreamService.createInterpretationDream(request), HttpStatus.CREATED.value());
    }

    @PostMapping("/select")
    public ResponseBody select(@Valid @RequestBody SelectInterpretationDreamRequest request) {
        return ResponseBody.of(interpretationDreamService.selectInterpretationDream(request), HttpStatus.OK.value());
    }

    @PutMapping("/{dream-uuid}")
    public ResponseBody modify(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody InterpretationDreamRequest request) {
        return ResponseBody.of(interpretationDreamService.modifyInterpretationDream(uuid, request), HttpStatus.OK.value());
    }

    @GetMapping("/search")
    public ResponseBody search(@RequestParam("title") String title, @RequestParam("types") String[] types) {
        return ResponseBody.listOf(interpretationDreamFindService.searchInterpretationDreams(title, types), HttpStatus.OK.value());
    }

    @GetMapping("/static")
    public ResponseBody interpretationDream() {
        return ResponseBody.listOf(interpretationDreamService.queryInterpretationDreamCategory(), HttpStatus.OK.value());
    }

    @GetMapping("/static/{interpretation-uuid}")
    public ResponseBody interpretationDetail(@PathVariable("interpretation-uuid") String uuid) {
        return ResponseBody.of(interpretationDreamService.queryInterpretation(uuid), HttpStatus.OK.value());
    }

    @GetMapping("/{dream-uuid}")
    public ResponseBody information(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.of(interpretationDreamFindService.queryInterpretationDreamResponse(uuid), HttpStatus.OK.value());
    }
}
