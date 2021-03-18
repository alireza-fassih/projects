package ir.fassih.homework.certmanager.rest.mapper;


import ir.fassih.homework.certmanager.entities.UserEntity;
import ir.fassih.homework.certmanager.rest.model.user.UserListDto;
import ir.fassih.homework.certmanager.rest.model.user.UserSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDtoMapper {

    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);


    UserListDto userToUserListDto(UserEntity user);


    UserEntity userSaveDtoToUserEntity(UserSaveDto dto);

}
