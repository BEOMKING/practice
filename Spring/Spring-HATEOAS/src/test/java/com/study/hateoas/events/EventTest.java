package com.study.hateoas.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class EventTest {
	@Test
	void builder() {
		// Given
		final Event event = Event.builder()
				.name("Inflearn Spring REST API")
				.description("REST API development with Spring")
				.build();

		// When
		final String name = event.getName();

		// Then
		assertThat(name).isEqualTo("Inflearn Spring REST API");
	}

	@Test
	void javaBean() {
		// Given
		final String name = "Event";
		final String description = "Spring";

		// When
		final Event event = new Event();
		event.setName(name);
		event.setDescription(description);

		// Then
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(description);
	}

	@Test
	void 가격이_있으면_무료가_아니다() {
		// Given
		final Event event = Event.builder()
				.name("Spring REST API")
				.description("REST API development with Spring")
				.basePrice(100)
				.maxPrice(200)
				.location("Think More")
				.build();

		// When
		event.update();

		// Then
		assertThat(event.isFree()).isFalse();
	}

	@Test
	void 가격이_없으면_무료이다() {
		// Given
		final Event event = Event.builder()
				.name("Spring REST API")
				.description("REST API development with Spring")
				.basePrice(0)
				.maxPrice(0)
				.location("Think More")
				.build();

		// When
		event.update();

		// Then
		assertThat(event.isFree()).isTrue();
	}

	@Test
	void 장소가_있으면_온라인이다() {
		// Given
		final Event event = Event.builder()
				.name("Spring REST API")
				.description("REST API development with Spring")
				.location("Think More")
				.build();

		// When
		event.update();

		// Then
		assertThat(event.isOffline()).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " "})
	@NullSource
	void 장소가_없으면_오프라인이다(final String location) {
		// Given
		final Event event = Event.builder()
				.name("Spring REST API")
				.description("REST API development with Spring")
				.location(location)
				.build();

		// When
		event.update();

		// Then
		assertThat(event.isOffline()).isFalse();
	}

	@ParameterizedTest
	@MethodSource("parametersForReturnFalseIfBasePriceAndMaxPriceHasValue")
	@DisplayName("BasePrice와 MaxPrice가 모두 0이면 무료, 아니면 무료가 아니다.")
	void returnFalseIfBasePriceAndMaxPriceHasValue(final int basePrice, final int maxPrice, final boolean isFree) {
		// Given
		final Event event = Event.builder()
				.basePrice(basePrice)
				.maxPrice(maxPrice)
				.build();

		// When
		event.update();

		// Then
		assertThat(event.isFree()).isEqualTo(isFree);
	}

	private static Stream<Arguments> parametersForReturnFalseIfBasePriceAndMaxPriceHasValue() {
		return Stream.of(
				Arguments.of(0, 0, true),
				Arguments.of(100, 0, false),
				Arguments.of(0, 100, false),
				Arguments.of(100, 200, false)
		);
	}
}
