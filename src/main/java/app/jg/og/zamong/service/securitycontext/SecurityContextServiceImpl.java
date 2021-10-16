package app.jg.og.zamong.service.securitycontext;

import app.jg.og.zamong.security.auth.AuthenticationDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextServiceImpl implements SecurityContextService {

    @Override
    public String getName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public AuthenticationDetails getPrincipal() {
        return (AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
