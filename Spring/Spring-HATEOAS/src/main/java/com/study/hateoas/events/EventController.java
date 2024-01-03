package com.study.hateoas.events;

import com.study.hateoas.index.IndexController;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    public EventController(final EventRepository eventRepository, final ModelMapper modelMapper,
                           final EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.eventValidator = eventValidator;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid final EventDto eventDto, final Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(EntityModel.of(errors, linkTo(methodOn(IndexController.class).index()).withRel("index")));
        }

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(EntityModel.of(errors, linkTo(methodOn(IndexController.class).index()).withRel("index")));
        }

        final Event savedEvent = eventRepository.save(modelMapper.map(eventDto, Event.class));
        savedEvent.update();

        final WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(savedEvent.getId());
        final URI createdUri = selfLinkBuilder.toUri();
        final EntityModel<Event> eventResource = EntityModel.of(savedEvent);
        eventResource.add(selfLinkBuilder.slash(savedEvent).withSelfRel());
        eventResource.add(selfLinkBuilder.withRel("query-events"));
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        eventResource.add(Link.of("/docs/index.html#resources-events-create").withRel("profile"));

        return ResponseEntity.created(createdUri).body(eventResource);
    }

    @GetMapping
    public ResponseEntity queryEvents(final Pageable pageable, final PagedResourcesAssembler<Event> assembler) {
        final Page<Event> events = this.eventRepository.findAll(pageable);
        final PagedModel<EntityModel<Event>> model = assembler.toModel(events, e -> EntityModel.of(e, linkTo(EventController.class).slash(e.getId()).withSelfRel()));
        model.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));
        return ResponseEntity.ok(model);
    }

    @GetMapping("/search/base-price")
    public ResponseEntity queryByBasePrice(
            @RequestParam("startBasePrice") final int startBasePrice,
            @RequestParam("endBasePrice") final int endBasePrice,
            final Pageable pageable,
            final PagedResourcesAssembler<Event> assembler) {
        final Page<Event> events = this.eventRepository.findByBasePriceBetween(startBasePrice, endBasePrice, pageable);
        final PagedModel<EntityModel<Event>> model = assembler.toModel(events, e -> EntityModel.of(e, linkTo(EventController.class).slash(e.getId()).withSelfRel()));
        model.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));
        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity queryEvent(@PathVariable final Integer id) {
        final Optional<Event> event = this.eventRepository.findById(id);

        if (event.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final EntityModel<Event> eventResource = EntityModel.of(event.get());
        eventResource.add(linkTo(EventController.class).slash(event.get().getId()).withSelfRel());
        eventResource.add(Link.of("/docs/index.html#resources-events-get").withRel("profile"));

        return ResponseEntity.ok(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity putEvent(@PathVariable("id") final Integer id, @RequestBody @Valid final EventDto eventDto, final Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(EntityModel.of(errors, linkTo(methodOn(IndexController.class).index()).withRel("index")));
        }
        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(EntityModel.of(errors, linkTo(methodOn(IndexController.class).index()).withRel("index")));
        }

        final Optional<Event> result = this.eventRepository.findById(id);

        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final Event event = result.get();
        event.change(eventDto);
        final Event save = this.eventRepository.save(event);

        final EntityModel<Event> eventResource = EntityModel.of(save);
        eventResource.add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
        eventResource.add(Link.of("/docs/index.html#resources-events-update").withRel("profile"));

        return ResponseEntity.ok(eventResource);
    }
}
