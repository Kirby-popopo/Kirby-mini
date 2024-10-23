package com.example.Mini1st.controller;
import com.example.Mini1st.dao.PostDTO;
import com.example.Mini1st.dao.login.UserDTO;
import jakarta.servlet.http.HttpSession;
import com.example.Mini1st.dao.mapper.PostMapper;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Profiles;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    PostMapper postDAO;

    @PostMapping("/posts/create")
    public ResponseEntity<?> createPost(@RequestParam("image") String base64Image,
                             @RequestParam("content") String contentBox,
                             Model model,
                             HttpSession session) { // setuserId 를 검사하기위해 세션사용
        System.out.println("호출");
        try {
            // Base64 문자열에서 메타데이터 제거 (예: "data:image/jpeg;base64," 제거)
            String[] parts = base64Image.split(",");
            String imageString = parts.length > 1 ? parts[1] : base64Image;

            // Base64 디코딩
            byte[] imageBytes = Base64.getDecoder().decode(imageString);

            // 저장할 경로 설정 - 프로젝트 디렉터리의 static/images 폴더
            String directory = new File("C:\\MiniProject\\Kirby-mini\\Mini1st\\src\\main\\resources\\static\\images").getAbsolutePath();
            String name = System.currentTimeMillis() + ".png";
            String filePath = directory + "/" + name;

            // 이미지 파일 저장
            File imageFile = new File(filePath);
            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                fos.write(imageBytes);
            }

            System.out.println("이미지 파일이 성공적으로 저장되었습니다: " + filePath);
            System.out.println("게시글 내용: " + contentBox);
            PostDTO insetData = new PostDTO();
            UserDTO findUser = (UserDTO)session.getAttribute("loginMember");
            insetData.setUserId(findUser.getUser_id());
            insetData.setContents(contentBox);
            insetData.setImage_link("/images/"+name);

            model.addAttribute("post",insetData);
            try {
                postDAO.insertPost(insetData);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            //리다이렉트로 게시물 저장되었을때 main-> post_list (무한스크롤)-> postModal상세페이지 로 이동
            // 리다이렉트로 프로파일 > 게시물 리스트 > 올린 게시물 팝업창 이동
            /*List<PostDTO> getAllPosts */
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    }

