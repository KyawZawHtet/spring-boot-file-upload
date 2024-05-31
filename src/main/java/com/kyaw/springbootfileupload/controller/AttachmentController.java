/**
 * @Author : Kyaw Zaw Htet
 * @Date : 5/31/2024
 * @Time : 12:33 PM
 * @Project_Name : spring-boot-file-upload
 */
package com.kyaw.springbootfileupload.controller;

import com.kyaw.springbootfileupload.ResponseData;
import com.kyaw.springbootfileupload.entity.Attachment;
import com.kyaw.springbootfileupload.service.AttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@AllArgsConstructor
public class AttachmentController {

    private AttachmentService attachmentService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseData> uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            Attachment attachment = attachmentService.saveAttachment(file);
            String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(attachment.getId().toString())
                    .toUriString();

            ResponseData responseData = new ResponseData(attachment.getFileName(), downloadURL, file.getContentType(), file.getSize());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseData);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseData("Error", e.getMessage(), null, 0L));
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId){
        try {
            Attachment attachment = attachmentService.getAttachment(fileId);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(attachment.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"")
                    .body(new ByteArrayResource(attachment.getData()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
