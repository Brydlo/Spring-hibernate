package sklep.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class PhotoUtil {
    private static final String EXT = ".jpg";

    @Value("${alx.photo_dir}")
    private String dir;

    public File getFile(int productId) {
        Path path = getPath(productId);
        File file = path.toFile();
        if(file.exists()) {
            return file;
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Brak zdjęcia nr " + productId);
        }
    }

    public byte[] readBytes(int productId) {
        Path path = getPath(productId);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Brak zdjęcia nr " + productId);
        }
    }

    public void writeStream(int productId, InputStream inputStream) {
        try {
            Path path = getPath(productId);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            // wypisujemy błąd, ale metoda kończy się normalnie
            e.printStackTrace();
        }
    }

    private Path getPath(int productId) {
        String fileName = productId + EXT;
        return Paths.get(dir, fileName);
    }

}


