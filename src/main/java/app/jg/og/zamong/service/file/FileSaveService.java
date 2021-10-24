package app.jg.og.zamong.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileSaveService {
    String saveFile(MultipartFile file, String directory);
    String queryHostName();
}
