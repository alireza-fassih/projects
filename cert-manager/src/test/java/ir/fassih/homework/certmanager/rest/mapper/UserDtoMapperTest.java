package ir.fassih.homework.certmanager.rest.mapper;

import ir.fassih.homework.certmanager.entities.UserEntity;
import ir.fassih.homework.certmanager.rest.model.user.UserListDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoMapperTest {


    @Test
    public void testSetIdAndUserName() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(10L);
        userEntity.setUsername("username");

        UserListDto dto = UserDtoMapper.INSTANCE.userToUserListDto(userEntity);
        assertEquals(10L , dto.getId());
        assertEquals("username", dto.getUsername());
    }
}