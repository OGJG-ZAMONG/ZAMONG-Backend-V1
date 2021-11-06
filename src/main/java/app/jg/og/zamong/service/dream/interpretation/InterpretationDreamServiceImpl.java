package app.jg.og.zamong.service.dream.interpretation;

import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamCategoryResponse;
import app.jg.og.zamong.entity.dream.interpretationdream.interpretation.Interpretation;
import app.jg.og.zamong.entity.dream.interpretationdream.interpretation.InterpretationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterpretationDreamServiceImpl implements InterpretationDreamService {

    private final InterpretationRepository interpretationRepository;

    @Override
    public InterpretationDreamCategoryResponse queryInterpretationDreamCategory() {
        List<Interpretation> interpretations = interpretationRepository.findAll();

        List<InterpretationDreamCategoryResponse.InterpretationDream> interpretationDreams = interpretations.stream()
                .map(id -> InterpretationDreamCategoryResponse.InterpretationDream.builder()
                        .uuid(id.getUuid())
                        .dreamName(id.getDreamName())
                        .build())
                .collect(Collectors.toList());

        return InterpretationDreamCategoryResponse.builder()
                .interpretationDreams(interpretationDreams)
                .build();
    }
}
