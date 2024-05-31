/**
 * @Author : Kyaw Zaw Htet
 * @Date : 5/31/2024
 * @Time : 12:24 PM
 * @Project_Name : spring-boot-file-upload
 */
package com.kyaw.springbootfileupload.repository;

import com.kyaw.springbootfileupload.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
