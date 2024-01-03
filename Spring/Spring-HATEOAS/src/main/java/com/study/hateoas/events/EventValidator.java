package com.study.hateoas.events;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {
    public void validate(final EventDto eventDto, final Errors errors) {
        throwErrorsIfInvalidPrice(eventDto, errors);
        throwErrorsIfInvalidEndEventDateTime(eventDto, errors);
        throwErrorsIfInvalidBeginEventDataTime(eventDto, errors);
        throwErrorsIfInvalidCloseEnrollmentDateTime(eventDto, errors);
    }

    private void throwErrorsIfInvalidPrice(final EventDto eventDto, final Errors errors) {
        if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0) {
            errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong.");
            errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is wrong.");
            errors.reject("wrongPrices", "Values for prices are wrong");
        }
    }

    private void throwErrorsIfInvalidEndEventDateTime(final EventDto eventDto, final Errors errors) {
        final LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();

        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
            endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
            endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong.");
        }
    }

    private void throwErrorsIfInvalidBeginEventDataTime(final EventDto eventDto, final Errors errors) {
        final LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
        final LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();

        if (beginEventDateTime != null && closeEnrollmentDateTime != null) {
            if (beginEventDateTime.isBefore(closeEnrollmentDateTime)) {
                errors.rejectValue("beginEventDateTime", "wrongValue", "이벤트 시작 시간은 등록 종료 시간보다 빠를 수 없습니다.");
            }
        }
    }

    private void throwErrorsIfInvalidCloseEnrollmentDateTime(final EventDto eventDto, final Errors errors) {
        final LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();
        final LocalDateTime beginEnrollmentDateTime = eventDto.getBeginEnrollmentDateTime();

        if (closeEnrollmentDateTime != null && beginEnrollmentDateTime != null) {
            if (closeEnrollmentDateTime.isBefore(beginEnrollmentDateTime)) {
                errors.rejectValue("closeEnrollmentDateTime", "wrongValue", "등록 종료 시간은 등록 시작 시간보다 빠를 수 없습니다.");
            }
        }
    }
}
