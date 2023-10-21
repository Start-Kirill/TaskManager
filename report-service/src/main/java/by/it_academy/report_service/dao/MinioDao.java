package by.it_academy.report_service.dao;

import by.it_academy.report_service.config.property.MinioProperty;
import by.it_academy.report_service.dao.api.IFileSystemDao;
import by.it_academy.report_service.dao.exceptions.SuchFileNotExists;
import by.it_academy.task_manager_common.dto.errors.ErrorResponse;
import by.it_academy.task_manager_common.enums.ErrorType;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
public class MinioDao implements IFileSystemDao {

    private final MinioClient minioClient;

    private static final String EXCEL_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";


    public MinioDao(MinioProperty minioProperty) {
        minioClient = MinioClient.builder()
                .endpoint(minioProperty.getUrl())
                .credentials(minioProperty.getCredentials().getUser(),
                        minioProperty.getCredentials().getPassword())
                .build();
    }

    @Override
    public void save(byte[] data, String fileName, String bucketName) {
        BucketExistsArgs bucket = BucketExistsArgs.builder().bucket(bucketName).build();
        try {
            if (!minioClient.bucketExists(bucket)) {
                MakeBucketArgs build = MakeBucketArgs.builder().bucket(bucketName).build();
                minioClient.makeBucket(build);
            }
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .contentType(EXCEL_CONTENT_TYPE)
                            .stream(new ByteArrayInputStream(data), data.length, -1)
                            .build()
            );
//            TODO
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUrl(String filename, String bucketName) {
        try {
            boolean isExists = this.minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build())
                    && this.minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build()) != null;
            if (!isExists) {
                throw new SuchFileNotExists(List.of(new ErrorResponse(ErrorType.ERROR, "The server was unable to process the request correctly. Please contact administrator")));
            }

            String presignedObjectUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .expiry(30000)
                            .method(Method.GET)
                            .build()
            );

            return presignedObjectUrl;

//            TODO
        } catch (ErrorResponseException e) {
            throw new RuntimeException(e);
        } catch (InsufficientDataException e) {
            throw new RuntimeException(e);
        } catch (InternalException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        } catch (XmlParserException e) {
            throw new RuntimeException(e);
        }

    }
}
