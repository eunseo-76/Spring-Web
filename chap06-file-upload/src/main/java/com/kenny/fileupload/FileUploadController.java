package com.kenny.fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class FileUploadController {
    // 넘어오는 데이터 (1) singleFile (2) singleFileDescription
    @PostMapping("/single-file")
    public String singleFileUpload(
            // @RequestParam 생략 가능. 넘어오는 데이터의 이름은 form 태그의 name 속성과 일치해야 한다.
            @RequestParam String singleFileDescription,
            @RequestParam MultipartFile singleFile,
            Model model
    ) {   // MultipartFile은 파일의 이름, 파일의 데이터 등 파일에 대한 정보를 가지고 있다.
        System.out.println("singleFileDescription: " + singleFileDescription);
        System.out.println("singleFile: " + singleFile);

        /* 서버로 전송된 MultipartFile 객체를 서버에서 지정하는 경로에 저장한다. */
        String root = "src/main/resources/static";  // 실존 경로
        String filePath = root + "/uploadFiles";    // /uploadFiles는 실존 경로가 아님 (아직 안 만듦)
        File dir = new File(filePath);

        if (!dir.exists()) dir.mkdir();

        // Multipart 타입으로 파일을 받았음. 이 파일은 trasferTo 메소드 사용 가능.

        // 파일 저장 시 고려할 점 : 파일이 동일한 이름으로 저장되면 덮어쓰기 될 수 있다.
        /* 파일명이 중복되면 해당 경로에서 덮어쓰기 될 수 있으므로 중복되지 않는 이름으로 변경 */

        String originFileName = singleFile.getOriginalFilename();   // 업로드 될 때 이름
        String ext = originFileName.substring(originFileName.lastIndexOf(".")); // originalFileName의 확장자(.pdf, .png 등) 부분을 추출
        String savedName = UUID.randomUUID() + ext; // randomUUID() : 영문자, 숫자, 하이픈을 섞어 랜덤한 고유 아이디를 만들어냄
        // 특정 dir 안에 저장할 파일이 1.pdf라고 가정. 또 다른 1.pdf가 이 dir에 저장될 수 있음.
        // 이름과 내용물까지 똑같은 파일이어도 이름만 바꿔서 저장한다는 뜻.


        /* 파일 저장 */
        try {
            singleFile.transferTo(new File(filePath + "/" + savedName));
            model.addAttribute("message", "파일 업로드 완료");
        } catch (IOException e) {
//            throw new RuntimeException(e);
            model.addAttribute("message", "파일 업로드 실패");
        }

        return "result";
    }

    @PostMapping("/multi-file")
    public String multiFileUpload(
            @RequestParam String multiFileDescription,
            @RequestParam List<MultipartFile> multiFiles,
            Model model
    ) {

        /* 서버로 전송된 MultipartFile 객체를 서버에서 지정하는 경로에 저장한다. */
        String root = "src/main/resources/static";  // 실존 경로
        String filePath = root + "/uploadFiles";    // /uploadFiles는 실존 경로가 아님 (아직 안 만듦)
        File dir = new File(filePath);

        if (!dir.exists()) dir.mkdir();

        List<FileDTO> files = new ArrayList<>();

        try {
            /* 다중 파일이므로 반복문을 이용한 처리를 하고 저장한다. */
            for(MultipartFile multiFile : multiFiles) {
                String originFileName = multiFile.getOriginalFilename();   // 업로드 될 때 이름
                String ext = originFileName.substring(originFileName.lastIndexOf(".")); // originalFileName의 확장자(.pdf, .png 등) 부분을 추출
                String savedName = UUID.randomUUID() + ext; // randomUUID() : 영문자, 숫자, 하이픈을 섞어 랜덤한 고유 아이디를 만들어냄

                /* 파일 저장 */
                multiFile.transferTo(new File(filePath + "/" + savedName));

                /* 파일에 관한 정보를 FileDTO에 담아 List에 보관 */
                files.add(new FileDTO(originFileName, savedName, filePath, multiFileDescription));
            }

            // files 정보는 DB에 insert 된다.
            model.addAttribute("message", "다중 파일 업로드 완료");

        } catch (IOException e) {

            // 파일 일부만 저장되고 다른 일부는 저장되지 않을 경우, 저장된 파일 삭제
            // 원래는 파일 이름을 db에도 insert 해야 함. 여기서는 생략하고 DTO만

            /* 파일 저장이 중간에 실패한 경우 이전에 저장된 파일 삭제 */
            for(FileDTO file : files) new File(filePath + "/" + file.getSavedFileName()).delete();
            model.addAttribute("message", "다중 파일 업로드 실패");

//            throw new RuntimeException(e);
        }
        return "result";
    }
}
