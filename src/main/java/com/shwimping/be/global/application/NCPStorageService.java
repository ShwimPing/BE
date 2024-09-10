package com.shwimping.be.global.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.shwimping.be.global.exception.FileConvertFailException;
import com.shwimping.be.global.exception.errorcode.GlobalErrorCode;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class NCPStorageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile multipartFile, String path) {
        // UUID를 사용하여 파일 이름 생성
        String fileName = UUID.randomUUID() + "_" + Objects.requireNonNull(multipartFile.getOriginalFilename());
        if (path.lastIndexOf("/") != path.length() - 1) {
            path += "/";
        }

        String fileLocation = path + fileName;
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(multipartFile.getContentType());
            amazonS3.putObject(new PutObjectRequest(bucket, fileLocation, multipartFile.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

            return amazonS3.getUrl(bucket, fileLocation).toString();
        } catch (IOException e) {
            throw new FileConvertFailException(GlobalErrorCode.FILE_CONVERT_FAIL);
        }
    }

    public void deleteFile(String fileUrl) {
        // URL에서 파일 이름 추출
        String fileName = fileUrl.substring(fileUrl.indexOf(bucket) + bucket.length() + 1);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        } catch (Exception e) {
            log.error("파일 삭제 실패: {}", e.getMessage());
            // 필요 시 적절한 예외 처리 추가
        }
    }
}
