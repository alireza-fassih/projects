package ir.fassih.homework.certmanager.rest.mapper;

import ir.fassih.homework.certmanager.entities.CertEntity;
import ir.fassih.homework.certmanager.rest.model.cert.CertListDto;
import ir.fassih.homework.certmanager.rest.model.cert.CertSaveDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CertDtoMapper {

    CertDtoMapper INSTANCE = Mappers.getMapper(CertDtoMapper.class);

    CertEntity certSaveDtoToVertEntity(CertSaveDto dto);


    CertListDto certEntityToCertListDto(CertEntity certEntity);

}
