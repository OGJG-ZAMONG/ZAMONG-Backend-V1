package app.jg.og.zamong.controller.dream;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamCostRequest;
import app.jg.og.zamong.dto.request.dream.selldream.SellDreamRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.sell.SellDreamService;
import app.jg.og.zamong.service.dream.sell.find.SellDreamFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.websocket.server.PathParam;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dream/sell")
public class SellDreamController {

    private final SellDreamService sellDreamService;

    @PostMapping()
    public ResponseBody sell(@Valid @RequestBody SellDreamRequest request) {
        return ResponseBody.of(sellDreamService.createSellDream(request), HttpStatus.CREATED.value());
    }

    @PostMapping("/{dream-uuid}/done")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void sellDone(@PathVariable("dream-uuid") String uuid) {
        sellDreamService.doneSellDream(uuid);
    }

    @DeleteMapping("/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void sellCancel(@PathVariable("dream-uuid") String uuid) {
        sellDreamService.cancelSellDream(uuid);
    }

    @PatchMapping("/cost/{dream-uuid}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void cost(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody SellDreamCostRequest request) {
        sellDreamService.patchSellDreamCost(uuid, request);
    }

    @PostMapping("/{dream-uuid}/request")
    public ResponseBody sellRequest(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.of(sellDreamService.doSellRequestDream(uuid), HttpStatus.OK.value());
    }

    private final SellDreamFindService sellDreamFindService;

    @GetMapping("/continue")
    public ResponseBody sellContinue(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size
    ) {
        return ResponseBody.listOf(sellDreamFindService.queryPendingSellDreams(page, size), HttpStatus.OK.value());
    }

    @GetMapping("/close")
    public ResponseBody sellClose(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size
    ) {
        return ResponseBody.listOf(sellDreamFindService.queryClosedSellDream(page, size), HttpStatus.OK.value());
    }
}
