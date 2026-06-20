package com.scm.peopledesk.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
  
  String uploadImage(MultipartFile profileImage, String filename);

  String getUrlFrompublicId(String publicId);

}
