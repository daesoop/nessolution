package nessolution.common.file.controller;

import nessolution.common.file.service.FileService;
import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/file")
public class FileController {
    private static final Logger logger = getLogger(FileController.class);


    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/list")
    public ResponseEntity listUploadedFiles() throws IOException {
        List ss = fileService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
               .collect(Collectors.toList());
        return new ResponseEntity(ss, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes) {
        fileService.store(file, file.getOriginalFilename());
        logger.info("success : " + file.getOriginalFilename());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/down/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }
}