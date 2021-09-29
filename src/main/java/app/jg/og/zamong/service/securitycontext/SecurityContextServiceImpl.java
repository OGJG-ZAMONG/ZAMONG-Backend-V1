package app.jg.og.zamong.service.securitycontext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    @Override
    public String getName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
