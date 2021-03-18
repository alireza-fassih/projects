package ir.fassih.homework.certmanager.rest;

import ir.fassih.homework.certmanager.rest.model.ActionResult;
import ir.fassih.homework.certmanager.rest.model.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SuppressWarnings("ALL")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String requestUsername;
    private String requestPassword;


    @BeforeEach
    public void setUp() {
        requestUsername = "wrong password";
        requestPassword = "wrong username";
    }

    @Test
    public void testAuthNotFoundInDatabase() {
        ResponseEntity<ActionResult> responseEntity = performRequest();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void loginSuccessfulWhenCredIsOk_checkResponse() {
        requestUsername = "root";
        requestPassword = "root1234";
        ResponseEntity<ActionResult> responseEntity = performRequest();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void checkCurrentUserInfo_when_loginSuccessFull() {
        requestUsername = "root";
        requestPassword = "root1234";
        ResponseEntity<ActionResult> loginResponse = performRequest();
        String cookie = loginResponse.getHeaders().getFirst("Set-Cookie");

        ResponseEntity<LoginDto> currentUserInfo = getCurrentUserInfo(cookie);

        LoginDto body = currentUserInfo.getBody();
        assertNotNull(body);
        assertEquals("root", body.getUsername());
    }

    @Test
    public void checkCurrentUserInfoWhen_loginNotSuccessful() {
        ResponseEntity<ActionResult> loginResponse = performRequest();
        String cookie = loginResponse.getHeaders().getFirst("Set-Cookie");
        ResponseEntity<LoginDto> currentUserInfo = getCurrentUserInfo(cookie);
        assertEquals(HttpStatus.FORBIDDEN, currentUserInfo.getStatusCode());
    }

    @Test
    public void checkCurrentUserInfoAfterLogout_shouldReturnForbidden() {
        requestUsername = "root";
        requestPassword = "root1234";

        ResponseEntity<ActionResult> loginResponse = performRequest();
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        String cookie = loginResponse.getHeaders().getFirst("Set-Cookie");

        ResponseEntity<Void> logoutResponse = performLogout(cookie);
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());

        ResponseEntity<LoginDto> currentUserInfo = getCurrentUserInfo(cookie);
        assertEquals(HttpStatus.FORBIDDEN, currentUserInfo.getStatusCode());
    }

    private ResponseEntity<Void> performLogout(String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie );

        return restTemplate.exchange("http://localhost:" + port + "/rest/auth/logout",
                HttpMethod.GET, new HttpEntity<>(headers), Void.class);
    }

    private ResponseEntity<ActionResult> performRequest() {
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword(requestPassword);
        loginDto.setUsername(requestUsername);
        return restTemplate.postForEntity("http://localhost:" + port + "/rest/auth/login", loginDto, ActionResult.class);
    }


    private ResponseEntity<LoginDto> getCurrentUserInfo(String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie );
        return restTemplate.exchange("http://localhost:" + port + "/rest/auth/info",
                HttpMethod.GET, new HttpEntity<>(headers), LoginDto.class);
    }
}