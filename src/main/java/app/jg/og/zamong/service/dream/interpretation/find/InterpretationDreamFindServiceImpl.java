package app.jg.og.zamong.service.dream.interpretation.find;

import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamGroupResponse;
import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamResponse;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDream;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDreamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterpretationDreamFindServiceImpl implements InterpretationDreamFindService {

    private final InterpretationDreamRepository interpretationDreamRepository;

    @Override
    public InterpretationDreamGroupResponse queryInterpretationDreams(int page, int size) {
        Pageable request = PageRequest.of(page, size, Sort.by("updatedAt").descending());

        Page<InterpretationDream> interpretationDreamPage = interpretationDreamRepository.findAll(request);

        return null;
    }

    private InterpretationDreamResponse interpretationDreamResponseOf(InterpretationDream interpretationDream) {
        return InterpretationDreamResponse.builder()
                .uuid(interpretationDream.getUuid())
                .defaultPostingImage(interpretationDream.getDefaultImage())
                .title(interpretationDream.getTitle())
                .dreamTypes(interpretationDream.getDreamTypes().stream().map(DreamType::getCode).collect(Collectors.toList()))
                .updatedAt(interpretationDream.getUpdatedAt())
                .user(InterpretationDreamResponse.User.builder()
                        .uuid(interpretationDream.getUser().getUuid())
                        .id(interpretationDream.getUser().getId())
                        .profile(interpretationDream.getUser().getProfile())
                        .build())
                .build();
    }
}
