package ir.fassih.homework.certmanager.rest;


import ir.fassih.homework.certmanager.manager.UploaderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/rest/upload")
public class UploadService {

    private static final String UUID_REGEX = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    @Autowired
    private UploaderManager uploaderManager;


    @PostMapping("/{id:"+UUID_REGEX+"}")
    public void handleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable("id") String id) throws IOException {
        uploaderManager.saveUpload(id, file.getBytes());
    }

    @DeleteMapping("/{id:"+UUID_REGEX+"}")
    public void deleteFile(@PathVariable("id") String id) {
        uploaderManager.removeUpload(id);
    }

}
