package ir.fassih.homework.certmanager.service.auth;

import ir.fassih.homework.certmanager.rest.model.LoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManualSpringSecurityService {

    private final AuthenticationManager authenticationManager;

    public void doLogin(LoginDto dto) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        SecurityContextHolder.getContext()
                .setAuthentication( authenticationManager.authenticate(token) );
    }


    public void doLogout() {
        SecurityContextHolder.clearContext();
    }


    public UserDetails getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        return (UserDetails) context.getAuthentication().getPrincipal();
    }

}
