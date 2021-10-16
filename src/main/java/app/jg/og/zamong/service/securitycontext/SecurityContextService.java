package app.jg.og.zamong.service.securitycontext;

import app.jg.og.zamong.security.auth.AuthenticationDetails;

public interface SecurityContextService {
    String getName();
    AuthenticationDetails getPrincipal();
}
