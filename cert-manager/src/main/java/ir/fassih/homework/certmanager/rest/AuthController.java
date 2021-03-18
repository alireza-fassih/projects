package ir.fassih.homework.certmanager.rest;

import ir.fassih.homework.certmanager.rest.model.ActionResult;
import ir.fassih.homework.certmanager.rest.model.LoginDto;
import ir.fassih.homework.certmanager.service.auth.ManualSpringSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ManualSpringSecurityService manualSpringSecurityService;

    @PostMapping("/login")
    public ActionResult<String> login(@RequestBody LoginDto dto ) {
        manualSpringSecurityService.doLogin( dto );
        return new ActionResult<>("login successful");
    }

    @GetMapping("/info")
    public UserDetails getAuthInfo() {
        return manualSpringSecurityService.getCurrentUser();
    }

    @GetMapping("/logout")
    public ActionResult<String> logout() {
        manualSpringSecurityService.doLogout();
        return new ActionResult<>("logout successful");
    }
}
