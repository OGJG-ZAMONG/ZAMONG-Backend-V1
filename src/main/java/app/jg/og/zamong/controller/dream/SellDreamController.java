package app.jg.og.zamong.controller.dream;

import app.jg.og.zamong.dto.request.dream.selldream.SellDreamChatRequest;
import app.jg.og.zamong.dto.request.dream.selldream.SellDreamCostRequest;
import app.jg.og.zamong.dto.request.dream.selldream.SellDreamRequest;
import app.jg.og.zamong.dto.response.ResponseBody;
import app.jg.og.zamong.service.dream.sell.SellDreamService;
import app.jg.og.zamong.service.dream.sell.chat.SellDreamChattingRoomService;
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

    @PutMapping("/{dream-uuid}")
    public ResponseBody modify(@PathVariable("dream-uuid") String uuid, @Valid @RequestBody SellDreamRequest request) {
        return ResponseBody.of(sellDreamService.modifySellDream(uuid, request), HttpStatus.OK.value());
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

    @GetMapping("/{dream-uuid}/request")
    public ResponseBody sellDreamRequest(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.listOf(sellDreamService.querySellDreamRequests(uuid), HttpStatus.OK.value());
    }

    @PostMapping("/{dream-uuid}/request")
    public ResponseBody sellRequest(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.of(sellDreamService.doSellRequestDream(uuid), HttpStatus.OK.value());
    }

    @PostMapping("/{sell-dream-request-uuid}/accept")
    public ResponseBody accept(@PathVariable("sell-dream-request-uuid") String uuid) {
        return ResponseBody.of(sellDreamService.acceptSellDreamRequest(uuid), HttpStatus.OK.value());
    }

    private final SellDreamFindService sellDreamFindService;

    @GetMapping("/continue")
    public ResponseBody sellContinue(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size
    ) {
        return ResponseBody.listOf(sellDreamFindService.queryPendingSellDreams(page, size), HttpStatus.OK.value());
    }

    @GetMapping("/continue/me")
    public ResponseBody sellMyContinueSellDream(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size
    ) {
        return ResponseBody.listOf(sellDreamFindService.queryMyPendingSellDreams(page, size), HttpStatus.OK.value());
    }

    @GetMapping("/close")
    public ResponseBody sellClose(
            @PathParam("page") int page,
            @Max(50) @PathParam("size") int size
    ) {
        return ResponseBody.listOf(sellDreamFindService.queryMyClosedSellDream(page, size), HttpStatus.OK.value());
    }

    @GetMapping("/search")
    public ResponseBody search(@PathParam("title") String title, @RequestParam("types") String[] types) {
        return ResponseBody.listOf(sellDreamFindService.searchSellDream(title, types), HttpStatus.OK.value());
    }

    @GetMapping("/{dream-uuid}")
    public ResponseBody information(@PathVariable("dream-uuid") String uuid) {
        return ResponseBody.of(sellDreamFindService.querySellDreamInformation(uuid), HttpStatus.OK.value());
    }

    private final SellDreamChattingRoomService sellDreamChattingRoomService;

    @GetMapping("/room")
    public ResponseBody room() {
        return ResponseBody.listOf(sellDreamChattingRoomService.queryChattingRoom(), HttpStatus.OK.value());
    }

    @PostMapping("/chat")
    public ResponseBody chat(@RequestBody SellDreamChatRequest request) {
        return ResponseBody.of(sellDreamChattingRoomService.createChat(request), HttpStatus.CREATED.value());
    }

    @GetMapping("/chat/{room-id}")
    public ResponseBody chats(
            @RequestParam("page") int page,
            @Max(50) @RequestParam("size") int size,
            @PathVariable("room-id") String uuid
    ) {
        return ResponseBody.listOf(sellDreamChattingRoomService.queryChats(uuid, page, size), HttpStatus.OK.value());
    }
}
