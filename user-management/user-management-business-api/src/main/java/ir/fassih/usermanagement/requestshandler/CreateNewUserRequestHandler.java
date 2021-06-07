package ir.fassih.usermanagement.requestshandler;

import ir.fassih.usermanagement.requests.CreateNewUserRequest;

public interface CreateNewUserRequestHandler {


    void createNewUser(CreateNewUserRequest request);


    class UserAlreadyExists extends RuntimeException {

    }

}
