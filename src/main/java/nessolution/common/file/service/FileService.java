package nessolution.common.file.service;

import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class FileService {

    private static final Logger logger = getLogger(FileService.class);


    Path rootLocation = Paths.get("C:\\Users\\kimda\\Documents\\Coding\\AP_MarKet\\src\\main\\webapp\\images");

    public void store(MultipartFile file, String strFileName) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        logger.info("testtest : " + filename);
        try {

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(strFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println("ddddd");
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(rootLocation::relativize);
        } catch (IOException e) {
            System.out.println("ddddd");
//            throw new StorageException("Failed to read stored files", e);
        }
        Stream ddd = null;
        return ddd;
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        Resource resource = null;

        try {
            Path file = load(filename);

            resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                System.out.println("ddddd");
//                throw new StorageFileNotFoundException(
//                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
//            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
            System.out.println("ddddd");
        }
        return resource;
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {

            Files.createDirectories(rootLocation);
        } catch (IOException e) {
//            throw new StorageException("Could not initialize storage", e);
            System.out.println("ddddd");
        }
    }
}
