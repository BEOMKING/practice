package com.study.hateoas.events;

import com.study.hateoas.SpringTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EventControllerTests extends SpringTestSupport {

    @Autowired
    private EventRepository eventRepository;

    @Test
    @DisplayName("정상적인 요청이 들어오면 201을 반환한다")
    void createEvent() throws Exception {
        // Given
        final EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 3, 22, 23, 30))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 3, 23, 23, 30))
                .beginEventDateTime(LocalDateTime.of(2023, 3, 24, 23, 30))
                .endEventDateTime(LocalDateTime.of(2023, 3, 25, 23, 30))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("Think More")
                .build();

        // When & Then
        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(eventDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE + ";charset=UTF-8"))
                .andExpect(jsonPath("free").value(false))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.query-events").exists())
                .andExpect(jsonPath("_links.update-event").exists())
                .andDo(document("create-event",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("query-events").description("link to query events"),
                                linkWithRel("update-event").description("link to update an existing event"),
                                linkWithRel("profile").description("link to profile")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("Description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("Date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("Date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("Date time of begin of new event"),
                                fieldWithPath("endEventDateTime").description("Date time of end of new event"),
                                fieldWithPath("location").description("Location of new event"),
                                fieldWithPath("basePrice").description("Base price of new event"),
                                fieldWithPath("maxPrice").description("Max price of new event"),
                                fieldWithPath("limitOfEnrollment").description("Limit of enrollment")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Identifier of new event"),
                                fieldWithPath("name").description("Name of new event"),
                                fieldWithPath("description").description("Description of new event"),
                                fieldWithPath("beginEnrollmentDateTime").description("Date time of begin of new event"),
                                fieldWithPath("closeEnrollmentDateTime").description("Date time of close of new event"),
                                fieldWithPath("beginEventDateTime").description("Date time of begin of new event"),
                                fieldWithPath("endEventDateTime").description("Date time of end of new event"),
                                fieldWithPath("location").description("Location of new event"),
                                fieldWithPath("basePrice").description("Base price of new event"),
                                fieldWithPath("maxPrice").description("Max price of new event"),
                                fieldWithPath("limitOfEnrollment").description("Limit of enrollment"),
                                fieldWithPath("free").description("It tells if this event is free or not"),
                                fieldWithPath("offline").description("It tells if this event is offline meeting or not"),
                                fieldWithPath("eventStatus").description("Event status"),
                                fieldWithPath("_links.self.href").ignored(),
                                fieldWithPath("_links.query-events.href").ignored(),
                                fieldWithPath("_links.update-event.href").ignored(),
                                fieldWithPath("_links.profile.href").ignored()
                        )
                ));
    }

    @Test
    void 불필요한_데이터가_함께_들어오면_400에러를_반환한다() throws Exception {
        // Given
        final Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 3, 22, 23, 30))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 3, 23, 23, 30))
                .beginEventDateTime(LocalDateTime.of(2023, 3, 24, 23, 30))
                .endEventDateTime(LocalDateTime.of(2023, 3, 25, 23, 30))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("Think More")
                .free(true)
                .offline(false)
                .build();

        // When & Then
        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void 유효하지않은_정보가_들어오면_400에러를_반환한다() throws Exception {
        // Given
        final EventDto eventDto = EventDto.builder()
                .name("Spring Rest API 교육")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 1, 9, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 3, 1, 9, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 3, 13, 16, 0))
                .endEventDateTime(LocalDateTime.of(2023, 4, 27, 17, 0))
                .location("Think More")
                .build();

        // When & Then
        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventDto))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("_links.index").exists());
    }

    @Test
    void 날짜값이_유효하지_않으면_400에러를_반환한다() throws Exception {
        // Given
        final EventDto eventDto1 = EventDto.builder()
                .name("Spring Rest API 교육")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 2, 1, 9, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 1, 1, 9, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 4, 13, 16, 0))
                .endEventDateTime(LocalDateTime.of(2023, 4, 27, 17, 0))
                .location("Think More")
                .build();

        final EventDto eventDto2 = EventDto.builder()
                .name("Spring Rest API 교육")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 1, 9, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 2, 1, 9, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 7, 13, 16, 0))
                .endEventDateTime(LocalDateTime.of(2023, 4, 27, 17, 0))
                .location("Think More")
                .build();

        // When & Then
        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventDto1))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("_links.index").exists());

        mockMvc.perform(post("/api/events").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventDto2))
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors[0].objectName").exists())
                .andExpect(jsonPath("errors[0].defaultMessage").exists())
                .andExpect(jsonPath("errors[0].code").exists())
                .andExpect(jsonPath("_links.index").exists());
    }

    @Test
    @DisplayName("이벤트 전체 조회에서 페이징된 이벤트 목록을 조회한다.")
    void selectAll() throws Exception {
        // Given
        IntStream.range(0, 30).forEach(this::generateEvent);

        // When
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/events")
                        .param("page", "1")
                        .param("size", "10")
                        .param("sort", "name,DESC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("page.size").value(10))
                .andDo(document("query-events",
                        links(
                                linkWithRel("first").description("첫 페이지"),
                                linkWithRel("prev").description("이전 페이지"),
                                linkWithRel("self").description("현재 페이지"),
                                linkWithRel("next").description("다음 페이지"),
                                linkWithRel("last").description("마지막 페이지"),
                                linkWithRel("profile").description("API 문서 링크")
                        ),
                        queryParameters(
                                parameterWithName("page").description("현재 페이지"),
                                parameterWithName("size").description("페이지당 사이즈"),
                                parameterWithName("sort").description("정렬 조건")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("_embedded.eventList[].id").description("이벤트 ID"),
                                fieldWithPath("_embedded.eventList[].name").description("이벤트 이름"),
                                fieldWithPath("_embedded.eventList[].description").description("이벤트 설명"),
                                fieldWithPath("_embedded.eventList[].beginEnrollmentDateTime").description("이벤트 등록 시작일"),
                                fieldWithPath("_embedded.eventList[].closeEnrollmentDateTime").description("이벤트 등록 종료일"),
                                fieldWithPath("_embedded.eventList[].beginEventDateTime").description("이벤트 시작일"),
                                fieldWithPath("_embedded.eventList[].endEventDateTime").description("이벤트 종료일"),
                                fieldWithPath("_embedded.eventList[].location").description("이벤트 장소"),
                                fieldWithPath("_embedded.eventList[].basePrice").description("이벤트 기본 가격"),
                                fieldWithPath("_embedded.eventList[].maxPrice").description("이벤트 최대 가격"),
                                fieldWithPath("_embedded.eventList[].limitOfEnrollment").description("이벤트 등록 제한"),
                                fieldWithPath("_embedded.eventList[].offline").description("오프라인 여부"),
                                fieldWithPath("_embedded.eventList[].free").description("무료 여부"),
                                fieldWithPath("_embedded.eventList[]._links.self.href").description("이벤트 링크"),
                                fieldWithPath("_links.first.href").description("첫 페이지 링크").ignored(),
                                fieldWithPath("_links.prev.href").description("이전 페이지 링크").ignored(),
                                fieldWithPath("_links.self.href").description("현재 페이지 링크").ignored(),
                                fieldWithPath("_links.next.href").description("다음 페이지 링크").ignored(),
                                fieldWithPath("_links.last.href").description("마지막 페이지 링크").ignored(),
                                fieldWithPath("_links.profile.href").description("API 문서 링크").ignored(),
                                fieldWithPath("page.size").description("페이지 사이즈"),
                                fieldWithPath("page.totalElements").description("전체 개수"),
                                fieldWithPath("page.totalPages").description("전체 페이지 수"),
                                fieldWithPath("page.number").description("현재 페이지 번호"))));
    }

    @Test
    @DisplayName("존재하는 이벤트에 대한 조회를 하면 해당 이벤트를 조회한다.")
    void getEvent() throws Exception {
        // Given
        final Event event = this.generateEvent(1);

        // When & Then
        this.mockMvc.perform(get("/api/events/{id}", event.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("query-event",
                        links(
                                linkWithRel("self").description("현재 페이지"),
                                linkWithRel("profile").description("API 문서 링크")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("id").description("이벤트 ID"),
                                fieldWithPath("name").description("이벤트 이름"),
                                fieldWithPath("description").description("이벤트 설명"),
                                fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작일"),
                                fieldWithPath("closeEnrollmentDateTime").description("이벤트 등록 종료일"),
                                fieldWithPath("beginEventDateTime").description("이벤트 시작일"),
                                fieldWithPath("endEventDateTime").description("이벤트 종료일"),
                                fieldWithPath("location").description("이벤트 장소"),
                                fieldWithPath("basePrice").description("이벤트 기본 가격"),
                                fieldWithPath("maxPrice").description("이벤트 최대 가격"),
                                fieldWithPath("limitOfEnrollment").description("이벤트 등록 제한"),
                                fieldWithPath("offline").description("오프라인 여부"),
                                fieldWithPath("free").description("무료 여부"),
                                fieldWithPath("_links.self.href").description("이벤트 링크").ignored(),
                                fieldWithPath("_links.profile.href").description("API 문서 링크").ignored()))
                );
    }

    @Test
    @DisplayName("없는 이벤트를 조회하면 404 응답을 받는다.")
    void return404WhenEventNotFound() throws Exception {
        // When & Then
        this.mockMvc.perform(get("/api/events/12345"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("특정 가격의 이벤트를 조회한다.")
    void returnEventByPrice() throws Exception {
        // Given
        IntStream.range(99, 201).forEach(i -> {
            final Event event = Event.builder()
                    .name("Rest API 교육" + i)
                    .description("사내 교육" + i)
                    .basePrice(i)
                    .build();

            this.eventRepository.save(event);
        });

        // When & Then
        this.mockMvc.perform(get("/api/events/search/base-price")
                        .param("startBasePrice", "100")
                        .param("endBasePrice", "200")
                        .param("page", "1")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                // 결과 리스트의 크기
                .andExpect(jsonPath("_embedded.eventList", hasSize(10)))
                .andExpect(jsonPath("_embedded.eventList[0].basePrice").exists())
                .andExpect(jsonPath("page.totalElements").value(101))
                .andDo(document("search-events-by-base-price",
                        links(
                                linkWithRel("self").description("현재 페이지"),
                                linkWithRel("profile").description("API 문서 링크"),
                                linkWithRel("first").description("첫 페이지"),
                                linkWithRel("prev").description("이전 페이지"),
                                linkWithRel("next").description("다음 페이지"),
                                linkWithRel("last").description("마지막 페이지")
                        ),
                        queryParameters(
                                parameterWithName("startBasePrice").description("이벤트 기본 가격 시작"),
                                parameterWithName("endBasePrice").description("이벤트 기본 가격 종료"),
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("_embedded.eventList[].id").description("이벤트 ID"),
                                fieldWithPath("_embedded.eventList[].name").description("이벤트 이름"),
                                fieldWithPath("_embedded.eventList[].description").description("이벤트 설명"),
                                fieldWithPath("_embedded.eventList[].beginEnrollmentDateTime").description("이벤트 등록 시작일"),
                                fieldWithPath("_embedded.eventList[].closeEnrollmentDateTime").description("이벤트 등록 종료일"),
                                fieldWithPath("_embedded.eventList[].beginEventDateTime").description("이벤트 시작일"),
                                fieldWithPath("_embedded.eventList[].endEventDateTime").description("이벤트 종료일"),
                                fieldWithPath("_embedded.eventList[].location").description("이벤트 장소"),
                                fieldWithPath("_embedded.eventList[].basePrice").description("이벤트 기본 가격"),
                                fieldWithPath("_embedded.eventList[].maxPrice").description("이벤트 최대 가격"),
                                fieldWithPath("_embedded.eventList[].limitOfEnrollment").description("이벤트 등록 제한"),
                                fieldWithPath("_embedded.eventList[].offline").description("오프라인 여부"),
                                fieldWithPath("_embedded.eventList[].free").description("무료 여부"),
                                fieldWithPath("_embedded.eventList[].eventStatus").description("이벤트 상태"),
                                fieldWithPath("_embedded.eventList[]._links.self.href").description("이벤트 링크"),
                                fieldWithPath("_links.first.href").description("첫 페이지 링크").ignored(),
                                fieldWithPath("_links.prev.href").description("이전 페이지 링크").ignored(),
                                fieldWithPath("_links.self.href").description("현재 페이지 링크").ignored(),
                                fieldWithPath("_links.next.href").description("다음 페이지 링크").ignored(),
                                fieldWithPath("_links.last.href").description("마지막 페이지 링크").ignored(),
                                fieldWithPath("_links.profile.href").description("API 문서 링크").ignored(),
                                fieldWithPath("page.size").description("페이지 사이즈"),
                                fieldWithPath("page.totalElements").description("전체 개수"),
                                fieldWithPath("page.totalPages").description("전체 페이지 수"),
                                fieldWithPath("page.number").description("현재 페이지 번호"))));
    }

    @Test
    @DisplayName("존재하는 이벤트에 대한 수정을 하면 해당 이벤트를 수정하고 수정된 이벤트를 반환한다.")
    void returnModifiedEventWhenEventIsExist() throws Exception {
        // Given
        final Event event = Event.builder()
                .name("Rest API 교육")
                .description("사내 교육")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 1, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 1, 31, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 2, 1, 0, 0))
                .endEventDateTime(LocalDateTime.of(2023, 2, 28, 0, 0))
                .limitOfEnrollment(10)
                .location("Think More")
                .build();
        final Event createdEvent = this.eventRepository.save(event);
        final EventDto modifiedEventDto = EventDto.builder()
                .name("Rest API 교육")
                .description("사내 교육 상급반")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 1, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 1, 31, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 2, 1, 0, 0))
                .endEventDateTime(LocalDateTime.of(2023, 2, 28, 0, 0))
                .limitOfEnrollment(15)
                .location("CC5")
                .build();

        // When & Then
        this.mockMvc.perform(put("/api/events/{id}", createdEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(modifiedEventDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("description").value(modifiedEventDto.getDescription()))
                .andExpect(jsonPath("beginEnrollmentDateTime").exists())
                .andExpect(jsonPath("closeEnrollmentDateTime").exists())
                .andExpect(jsonPath("beginEventDateTime").exists())
                .andExpect(jsonPath("endEventDateTime").exists())
                .andExpect(jsonPath("limitOfEnrollment").value(modifiedEventDto.getLimitOfEnrollment()))
                .andExpect(jsonPath("location").value(modifiedEventDto.getLocation()))
                .andExpect(jsonPath("offline").value(true))
                .andExpect(jsonPath("free").exists())
                .andExpect(jsonPath("basePrice").exists())
                .andExpect(jsonPath("maxPrice").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("put-event",
                                links(
                                        linkWithRel("self").description("현재 페이지"),
                                        linkWithRel("profile").description("API 문서 링크")
                                ),
                                requestFields(
                                        fieldWithPath("name").description("변경할 이벤트 이름"),
                                        fieldWithPath("description").description("변경할 이벤트 설명"),
                                        fieldWithPath("beginEnrollmentDateTime").description("변경할 이벤트 등록 시작일"),
                                        fieldWithPath("closeEnrollmentDateTime").description("변경할 이벤트 등록 종료일"),
                                        fieldWithPath("beginEventDateTime").description("변경할 이벤트 시작일"),
                                        fieldWithPath("endEventDateTime").description("변경할 이벤트 종료일"),
                                        fieldWithPath("location").description("변경할 이벤트 장소"),
                                        fieldWithPath("limitOfEnrollment").description("변경할 이벤트 등록 제한"),
                                        fieldWithPath("basePrice").description("변경할 이벤트 기본 가격"),
                                        fieldWithPath("maxPrice").description("변경할 이벤트 최대 가격")
                                ),
                                responseFields(
                                        fieldWithPath("id").description("이벤트 ID"),
                                        fieldWithPath("name").description("변경된 이벤트 이름"),
                                        fieldWithPath("description").description("변경된 이벤트 설명"),
                                        fieldWithPath("beginEnrollmentDateTime").description("변경된 이벤트 등록 시작일"),
                                        fieldWithPath("closeEnrollmentDateTime").description("변경된 이벤트 등록 종료일"),
                                        fieldWithPath("beginEventDateTime").description("변경된 이벤트 시작일"),
                                        fieldWithPath("endEventDateTime").description("변경된 이벤트 종료일"),
                                        fieldWithPath("location").description("변경된 이벤트 장소"),
                                        fieldWithPath("basePrice").description("변경된 이벤트 기본 가격"),
                                        fieldWithPath("maxPrice").description("변경된 이벤트 최대 가격"),
                                        fieldWithPath("limitOfEnrollment").description("변경된 이벤트 등록 제한"),
                                        fieldWithPath("offline").description("오프라인 여부"),
                                        fieldWithPath("free").description("무료 여부"),
                                        fieldWithPath("eventStatus").description("이벤트 상태"),
                                        fieldWithPath("_links.self.href").description("이벤트 링크").ignored(),
                                        fieldWithPath("_links.profile.href").description("API 문서 링크").ignored())
                        )
                );
    }

    @Test
    @DisplayName("존재하지 않는 이벤트에 대한 수정을 하면 404 응답을 받는다.")
    void return404WhenEventIsNotExist() throws Exception {
        // Given
        final EventDto modifiedEventDto = EventDto.builder()
                .name("Rest API 교육")
                .description("사내 교육 상급반")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 1, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 1, 31, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 2, 1, 0, 0))
                .endEventDateTime(LocalDateTime.of(2023, 2, 28, 0, 0))
                .limitOfEnrollment(15)
                .location("CC5")
                .build();

        // When & Then
        this.mockMvc.perform(put("/api/events/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(modifiedEventDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("입력값이 비어있는 경우에 대한 수정을 하면 400 응답을 받는다.")
    void return400WhenInputEmpty() throws Exception {
        // Given
        final Event event = Event.builder()
                .name("Rest API 교육")
                .description("사내 교육")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 1, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 1, 31, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 2, 1, 0, 0))
                .endEventDateTime(LocalDateTime.of(2023, 2, 28, 0, 0))
                .limitOfEnrollment(10)
                .location("Think More")
                .build();
        final Event createdEvent = this.eventRepository.save(event);
        final EventDto modifiedEventDto = EventDto.builder().build();

        // When & Then
        this.mockMvc.perform(put("/api/events/{id}", createdEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(modifiedEventDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("입력값이 잘못된 경우에 대한 수정을 하면 400 응답을 받는다.")
    void return400WhenInputWrong() throws Exception {
        // Given
        final Event event = Event.builder()
                .name("Rest API 교육")
                .description("사내 교육")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 1, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 1, 31, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 2, 1, 0, 0))
                .endEventDateTime(LocalDateTime.of(2023, 2, 28, 0, 0))
                .limitOfEnrollment(10)
                .location("Think More")
                .build();
        final Event createdEvent = this.eventRepository.save(event);
        final EventDto beginEventIsAfterThenEndEvent = EventDto.builder()
                .name("Rest API 교육")
                .description("사내 교육")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 1, 1, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 1, 31, 0, 0))
                .beginEventDateTime(LocalDateTime.of(2023, 2, 1, 0, 0))
                .endEventDateTime(LocalDateTime.of(2023, 1, 28, 0, 0))
                .limitOfEnrollment(10)
                .location("Think More")
                .build();

        // When & Then
        this.mockMvc.perform(put("/api/events/{id}", createdEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(beginEventIsAfterThenEndEvent)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private Event generateEvent(final int index) {
        final Event event = Event.builder()
                .name("event" + index)
                .description("Test Event")
                .build();

        return this.eventRepository.save(event);
    }
}

