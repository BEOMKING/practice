package jpabook.jpashop.controller;

import java.util.List;
import jpabook.jpashop.controller.dto.study.response.StudyInfoResponseDto;
import jpabook.jpashop.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study")
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/swaager")
    public List<StudyInfoResponseDto> getAllStudy() {
        return studyService.getAllStudy();
    }

//    @GetMapping("/")
//    public HttpStatus getAllStudy()  {
//        return HttpStatus.OK;
//    }
}