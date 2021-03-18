package ir.fassih.workshopautomation.rest;


import ir.fassih.workshopautomation.entity.user.UserEntity;
import ir.fassih.workshopautomation.manager.*;
import ir.fassih.workshopautomation.security.PortalRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Secured("ADMIN")
@RestController
@RequestMapping("/rest/user")
public class UserService  extends  AbstractRestService<UserEntity , Long> {

    private UserManager getMyManager() {
        return (UserManager) manager;
    }

    @Autowired
    public UserService(UserManager manager) {
        super(manager);
    }


    @Override
    protected Map<Class<? extends AbstractManager>, String> getOptionsMetadata() {
        Map<Class<? extends AbstractManager>, String> metadata = super.getOptionsMetadata();
        metadata.put(PriceListManager.class, "priceList");
        return metadata;
    }

    @Override
    public Map<String, Object> optionsInternal() {
        Map<String, Object> options = super.optionsInternal();
        options.put("roles", PortalRole.values());
        return options;
    }


    @PostMapping("/{id}/enable")
    public void enableUser(@PathVariable("id") Long id) {
        getMyManager().setEnable(id, true);
    }

    @PostMapping("/{id}/disable")
    public void disableUser(@PathVariable("id") Long id) {
        getMyManager().setEnable(id, false);
    }

}
