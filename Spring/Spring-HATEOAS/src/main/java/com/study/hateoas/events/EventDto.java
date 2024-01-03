package com.study.hateoas.events;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EventDto {
    @NotBlank
    private final String name;
    @NotBlank
    private final String description;
    @NotNull
    private final LocalDateTime beginEnrollmentDateTime;
    @NotNull
    private final LocalDateTime closeEnrollmentDateTime;
    @NotNull
    private final LocalDateTime beginEventDateTime;
    @NotNull
    private final LocalDateTime endEventDateTime;
    private final String location; // (optional) 이게 없으면 온라인 모임
    @Min(0)
    private final int basePrice; // (optional)
    @Min(0)
    private final int maxPrice; // (optional)
    @Min(0)
    private final int limitOfEnrollment;

    public EventDto(final String name, final String description,
        final LocalDateTime beginEnrollmentDateTime,
        final LocalDateTime closeEnrollmentDateTime, final LocalDateTime beginEventDateTime,
        final LocalDateTime endEventDateTime, final String location, final int basePrice,
        final int maxPrice,
        final int limitOfEnrollment) {
        this.name = name;
        this.description = description;
        this.beginEnrollmentDateTime = beginEnrollmentDateTime;
        this.closeEnrollmentDateTime = closeEnrollmentDateTime;
        this.beginEventDateTime = beginEventDateTime;
        this.endEventDateTime = endEventDateTime;
        this.location = location;
        this.basePrice = basePrice;
        this.maxPrice = maxPrice;
        this.limitOfEnrollment = limitOfEnrollment;
    }
}
