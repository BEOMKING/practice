package jpabook.jpashop.service;

import java.util.ArrayList;
import java.util.List;
import jpabook.jpashop.controller.dto.study.response.StudyInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
// https://velog.io/@kdhyo/JavaTransactional-Annotation-%EC%95%8C%EA%B3%A0-%EC%93%B0%EC%9E%90-26her30h
@RequiredArgsConstructor
public class StudyServiceImpl implements StudyService {

    public List<StudyInfoResponseDto> getAllStudy() {
        List<StudyInfoResponseDto> studyInfoResponseDtos = new ArrayList<>();

        return studyInfoResponseDtos;
    }


}
