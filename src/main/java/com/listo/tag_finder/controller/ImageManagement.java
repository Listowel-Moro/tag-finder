package com.listo.tag_finder.controller;

import com.listo.tag_finder.service.S3Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;

@Controller
public class ImageManagement {
    private final S3Service s3Service;

    public ImageManagement(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/")
    public String getImages(@RequestParam(value = "token", required = false) String token, Model model) {
        String continuationToken = token;

        List<Object> response = s3Service.listObjects("tag-finder-s3", continuationToken, "8");
        List<String> images = (List<String>) response.get(0);

        continuationToken = (String) response.get(1);
        model.addAttribute("nextToken", continuationToken);
        model.addAttribute("images", images);
        return "index";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("image") MultipartFile tagImage, RedirectAttributes redirectAttributes) throws IOException {
        if (tagImage.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select an image to upload.");
            return "redirect:/";
        }

        // Upload image to S3
        String bucketName = "tag-finder-s3";
        String fileKey = "listo_" + tagImage.getOriginalFilename();

        s3Service.uploadFile(bucketName, fileKey, tagImage);

//        try {
//            // Convert image to Base64 to display in
//            String base64Image = Base64.getEncoder().encodeToString(tagImage.getBytes());
//
//            // Redirect to display page with image data
//            redirectAttributes.addFlashAttribute("imageData", base64Image);
//            return "redirect:/";
//        } catch (IOException e) {
//            redirectAttributes.addFlashAttribute("message", "File processing failed.");
//            e.printStackTrace();
//            return "redirect:/";
//        }

        return "redirect:/";
    }

    @GetMapping("/display")
    public String showImagePage() {
        return "new-image";
    }

    @PostMapping("/delete-image")
    public String deleteImage(@RequestParam("imageKey") String imageKey) {
        System.out.println("Deleting image: " + imageKey);
        s3Service.deleteObject("tag-finder-s3", imageKey);
        return "redirect:/";
    }
}
