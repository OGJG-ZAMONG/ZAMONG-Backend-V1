package app.jg.og.zamong.service.dream.interpretation.find;

import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamGroupResponse;
import app.jg.og.zamong.dto.response.dream.interpretationdream.InterpretationDreamResponse;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDream;
import app.jg.og.zamong.entity.dream.interpretationdream.InterpretationDreamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
}
