package by.it_academy.report_service.service.api;

import java.util.UUID;

public interface IMinioService {

    void save(byte[] data, String fileName, String bucketName);

    String getUrl(UUID report);

}
