package com.example.Mini1st.controller;
import com.example.Mini1st.dao.PostDTO;
import com.example.Mini1st.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@RestController
public class PostController {
    @Autowired
    PostMapper postDAO;

    @PostMapping("/posts")
    public String createPost(@RequestParam("base64Image") String base64Image,
                             @RequestParam("content_box") String contentBox) {
        try {
            // Base64 문자열에서 메타데이터 제거 (예: "data:image/jpeg;base64," 제거)
            String[] parts = base64Image.split(",");
            String imageString = parts.length > 1 ? parts[1] : base64Image;

            // Base64 디코딩
            byte[] imageBytes = Base64.getDecoder().decode(imageString);

            // 저장할 경로 설정 - 프로젝트 디렉터리의 static/images 폴더
            String directory = new File("src/main/resources/static/images").getAbsolutePath();
            String filePath = directory + "/uploaded_image_" + System.currentTimeMillis() + ".png";

            // 이미지 파일 저장
            File imageFile = new File(filePath);
            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                fos.write(imageBytes);
            }

            System.out.println("이미지 파일이 성공적으로 저장되었습니다: " + filePath);
            System.out.println("게시글 내용: " + contentBox);
            PostDTO insetData = new PostDTO();
            insetData.setUserId("1234");
            insetData.setContents(contentBox);

            try {
                postDAO.insertPost(insetData);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            return "mainPage";
        } catch (IOException e) {
            e.printStackTrace();
            return "이미지 업로드 중 오류가 발생했습니다.";
        }
    }
}
