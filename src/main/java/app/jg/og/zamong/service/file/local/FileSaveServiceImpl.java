package app.jg.og.zamong.service.file.local;

import app.jg.og.zamong.exception.externalinfra.FileSaveFailedException;
import app.jg.og.zamong.service.file.FileSaveService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileSaveServiceImpl implements FileSaveService {

    @Value("${file.local.host}")
    private String localHostName;

    @Value("${file.local.path}")
    private String localPath;

    @Override
    public String saveFile(MultipartFile file, String directory) {
        try {
            String path = "/" + directory + "/" + UUID.randomUUID() + file.getOriginalFilename();
            file.transferTo(new File(localPath + path));
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileSaveFailedException();
        }
    }

    @Override
    public String queryHostName() {
        return localHostName;
    }
}
