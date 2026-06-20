package com.scm.peopledesk.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.peopledesk.helpers.AppConstants;
import com.scm.peopledesk.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

  private Cloudinary cloudinary;

  public ImageServiceImpl(Cloudinary cloudinary) {
    this.cloudinary = cloudinary;
  }

  @Override
  public String uploadImage(MultipartFile profileImage, String filename) {
    // write code for image uploading

    

    try {

      byte[] data = new byte[profileImage.getInputStream().available()];

      profileImage.getInputStream().read(data);
      cloudinary.uploader().upload(data, ObjectUtils.asMap(
          "public_id", filename));

      // return url
      return this.getUrlFrompublicId(filename);

    } catch (IOException e) {

      e.printStackTrace();

      return null;
    }

  }

  @Override
  public String getUrlFrompublicId(String publicId) {

    return cloudinary
        .url()
        .transformation(
            new Transformation<>()
                .width(AppConstants.CONTACT_IMAGE_WIDTH)
                .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                .crop(AppConstants.CONTACT_IMAGE_CROP)

        )
        .generate(publicId);
  }

}
