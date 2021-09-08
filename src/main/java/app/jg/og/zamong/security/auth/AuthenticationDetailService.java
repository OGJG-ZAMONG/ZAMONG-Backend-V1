package app.jg.og.zamong.security.auth;

import app.jg.og.zamong.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public AuthenticationDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUuid(username)
                .map(AuthenticationDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found Username " + username));
    }
}
