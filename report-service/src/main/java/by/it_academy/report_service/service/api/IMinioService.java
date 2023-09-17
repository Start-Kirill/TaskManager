package by.it_academy.report_service.service.api;

import java.util.UUID;

public interface IMinioService {

    void save(String data, String fileName, String bucketName);

    String getUrl(UUID report);

}
