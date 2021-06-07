package ir.fassih.usermanagement.business.requesthandler.impl;

import ir.fassih.usermanagement.dao.IUserEntityDao;
import ir.fassih.usermanagement.entities.UserEntity;
import ir.fassih.usermanagement.requests.CreateNewUserRequest;
import ir.fassih.usermanagement.requestshandler.CreateNewUserRequestHandler;

import javax.inject.Inject;
import javax.inject.Named;


@Named
public class CreateNewUserRequestHandlerImpl implements CreateNewUserRequestHandler {

    private final IUserEntityDao dao;

    @Inject
    public CreateNewUserRequestHandlerImpl(IUserEntityDao dao) {
        this.dao = dao;
    }

    @Override
    public void createNewUser(CreateNewUserRequest request) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(request.getPassword());

        dao.save(userEntity);
    }
}
