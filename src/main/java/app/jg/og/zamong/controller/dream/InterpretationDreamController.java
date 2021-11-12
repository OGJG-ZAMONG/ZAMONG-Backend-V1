package app.jg.og.zamong.controller.dream;

import app.jg.og.zamong.dto.request.dream.interpretationdream.InterpretationDreamRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.interpretation.InterpretationDreamService;
import app.jg.og.zamong.service.dream.interpretation.find.InterpretationDreamFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.websocket.server.PathParam;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dream/interpretation")
public class InterpretationDreamController {

    private final InterpretationDreamService interpretationDreamService;
    private final InterpretationDreamFindService interpretationDreamFindService;

    @GetMapping
    public ResponseBody interpretation(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size
    ) {
        return ResponseBody.listOf(interpretationDreamFindService.queryInterpretationDreams(page, size), HttpStatus.OK.value());
    }

    @PostMapping
    public ResponseBody interpretation(@Valid @RequestBody InterpretationDreamRequest request) {
        return ResponseBody.of(interpretationDreamService.createInterpretationDream(request), HttpStatus.CREATED.value());
    }

    @GetMapping("/static")
    public ResponseBody interpretationDream() {
        return ResponseBody.listOf(interpretationDreamService.queryInterpretationDreamCategory(), HttpStatus.OK.value());
    }

    @GetMapping("/static/{interpretation-uuid}")
    public ResponseBody interpretationDetail(@PathVariable("interpretation-uuid") String uuid) {
        return ResponseBody.of(interpretationDreamService.queryInterpretation(uuid), HttpStatus.OK.value());
    }
}
