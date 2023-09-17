package by.it_academy.report_service.dao.api;

public interface IFileSystemDao {

    void save(byte[] data, String fileName, String bucketName);

    String getUrl(String filename, String bucketName);
}
