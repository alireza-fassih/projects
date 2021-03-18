package ir.fassih.homework.certmanager.rest;


import ir.fassih.homework.certmanager.entities.CertEntity;
import ir.fassih.homework.certmanager.manager.CertManager;
import ir.fassih.homework.certmanager.manager.UploaderManager;
import ir.fassih.homework.certmanager.rest.mapper.CertDtoMapper;
import ir.fassih.homework.certmanager.rest.model.ActionResult;
import ir.fassih.homework.certmanager.rest.model.cert.CertListDto;
import ir.fassih.homework.certmanager.rest.model.cert.CertSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/certs")
@RequiredArgsConstructor
public class CertController {

    private final CertManager certManager;
    private final UploaderManager uploaderManager;

    @PostMapping
    public ActionResult<String> save(@RequestBody CertSaveDto dto) {
        CertEntity certEntity = CertDtoMapper.INSTANCE.certSaveDtoToVertEntity(dto);
        certEntity.setData(uploaderManager.readFile(dto.getImageId().toString()));
        certManager.save(certEntity);
        return new ActionResult<>("saved");
    }



    @GetMapping("/search")
    public List<CertListDto> search(
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "db", required = false, defaultValue = "false") boolean disableBase64) {
        if( !disableBase64 && StringUtils.hasText(query) ) {
            query = new String(Base64.getDecoder().decode(query));
        }
        return certManager.searchByQuery(query)
                .stream().map(CertDtoMapper.INSTANCE::certEntityToCertListDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ActionResult<String> delete(@PathVariable("id") long id) {
        certManager.delete(id);
        return new ActionResult<>("success");
    }


    @GetMapping("/loadCert/{id}")
    @ResponseBody
    public byte[] loadImages(@PathVariable("id") long id) {
        return certManager.load(id).getData();
    }
}
