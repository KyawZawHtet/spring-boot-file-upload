/**
 * @Author : Kyaw Zaw Htet
 * @Date : 5/31/2024
 * @Time : 12:32 PM
 * @Project_Name : spring-boot-file-upload
 */
package com.kyaw.springbootfileupload.service.serviceimpl;

import com.kyaw.springbootfileupload.entity.Attachment;
import com.kyaw.springbootfileupload.repository.AttachmentRepository;
import com.kyaw.springbootfileupload.service.AttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private AttachmentRepository attachmentRepository;

    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if(fileName.contains("..")){
                throw new Exception("Filename contains invalid path sequence " + fileName);
            }

            Attachment attachment = new Attachment(fileName, file.getContentType(), file.getBytes());
            return attachmentRepository.save(attachment);
        }catch (Exception e){
            throw new Exception("Could not save file: " + fileName, e);
        }
    }

    @Override
    public Attachment getAttachment(Long fileId){
        try {
            return attachmentRepository.findById(fileId).orElseThrow(
                    () -> new Exception("File not found with id " + fileId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
