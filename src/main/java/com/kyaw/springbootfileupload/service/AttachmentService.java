/**
 * @Author : Kyaw Zaw Htet
 * @Date : 5/31/2024
 * @Time : 12:31 PM
 * @Project_Name : spring-boot-file-upload
 */
package com.kyaw.springbootfileupload.service;

import com.kyaw.springbootfileupload.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(Long fileId) throws Exception;
}
