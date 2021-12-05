package app.jg.og.zamong.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileSaveService {
    String saveFile(MultipartFile file, String directory);
    String queryHostName();

    String[] DEFAULT_IMAGES = {
            "/default/261136866_1110756623071666_1493731416713374900_n.jpg",
            "/default/261623756_3064940033827332_3162041196258800389_n.jpg",
            "/default/261971779_4918168671547521_4777906569227684602_n.jpg",
            "/default/262773889_629183421838479_6164581791855682657_n.jpg",
            "/default/263192123_300824088599141_7244668161936722444_n.jpg"
    };
}
