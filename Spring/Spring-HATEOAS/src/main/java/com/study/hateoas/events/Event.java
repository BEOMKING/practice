package com.study.hateoas.events;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String description;
	private LocalDateTime beginEnrollmentDateTime;
	private LocalDateTime closeEnrollmentDateTime;
	private LocalDateTime beginEventDateTime;
	private LocalDateTime endEventDateTime;
	private String location; // (optional) 이게 없으면 온라인 모임
	private int basePrice; // (optional)
	private int maxPrice; // (optional)
	private int limitOfEnrollment;
	private boolean offline;
	private boolean free;

	@Enumerated(EnumType.STRING)
	private final EventStatus eventStatus = EventStatus.DRAFT;

	public void update() {
		updateFree();
		updateOffline();
	}

	private void updateFree() {
		free = (basePrice == 0 && maxPrice == 0);
	}

	private void updateOffline() {
		offline = (location != null && !location.isBlank());
	}

	public void change(final EventDto eventDto) {
		this.name = eventDto.getName();
		this.description = eventDto.getDescription();
		this.beginEnrollmentDateTime = eventDto.getBeginEnrollmentDateTime();
		this.closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();
		this.beginEventDateTime = eventDto.getBeginEventDateTime();
		this.endEventDateTime = eventDto.getEndEventDateTime();
		this.location = eventDto.getLocation();
		this.basePrice = eventDto.getBasePrice();
		this.maxPrice = eventDto.getMaxPrice();
		this.limitOfEnrollment = eventDto.getLimitOfEnrollment();
		update();
	}
}
