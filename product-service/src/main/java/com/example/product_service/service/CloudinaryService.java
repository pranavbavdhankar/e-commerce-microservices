package com.example.product_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary = new Cloudinary(System.getenv("CLOUDINARY_URL"));

    public List<String> uploadFiles(ArrayList<MultipartFile> files){
        ArrayList<String> res = new ArrayList<>();
        for(MultipartFile file : files){
            String url = uploadFile(file);
            res.add(url);
        }
        return res;
    }

    public String uploadFile(MultipartFile file){
        try{
            Map params1 = ObjectUtils.asMap(
                    "use_filename", false,
                    "unique_filename", true
            );
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            File tempFile = File.createTempFile(UUID.randomUUID().toString(), extension);
            try(FileOutputStream fos = new FileOutputStream(tempFile)){
                fos.write(file.getBytes());
            }
            Map uploadedFile = cloudinary.uploader().upload(tempFile, params1);
            return ((String)uploadedFile.get("secure_url"));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteFiles(List<String> files) {

        if(files == null) return;
        for(String file : files){
            deleteFile(file);
        }

    }

    public void deleteFile(String file){
        try{
            String publicId = file.substring( file.lastIndexOf('/')+1, file.lastIndexOf('.'));
            cloudinary.uploader().destroy(publicId, null);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
