package app.jg.og.zamong.service.file.s3;

import app.jg.og.zamong.exception.externalinfra.FileSaveFailedException;
import app.jg.og.zamong.service.file.FileSaveService;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FileSaveServiceImpl implements FileSaveService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String saveFile(MultipartFile file, String directory) {
        try {
            File uploadFile = convert(file)
                    .orElseThrow(FileSaveFailedException::new);
            return saveFile(uploadFile, directory);
        } catch (IOException e) {
            throw new FileSaveFailedException();
        }
    }

    private String saveFile(File file, String directory) {
        String fileName = directory + "/" + file.getName();
        String uploadFilePath = putS3(file, fileName);
        file.delete();
        return uploadFilePath;
    }

    private String putS3(File file, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).getPath();
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try(FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    @Value("${cloud.aws.s3.host}")
    private String s3Host;

    @Override
    public String queryHostName() {
        return s3Host;
    }
}
