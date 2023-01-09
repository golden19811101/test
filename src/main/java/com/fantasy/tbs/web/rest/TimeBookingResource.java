package com.fantasy.tbs.web.rest;

import com.fantasy.tbs.domain.TimeBooking;
import com.fantasy.tbs.repository.TimeBookingRepository;
import com.fantasy.tbs.service.TimeBookingService;
import com.fantasy.tbs.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.fantasy.tbs.domain.TimeBooking}.
 */
@RestController
@RequestMapping("/api")
public class TimeBookingResource {

    private final Logger log = LoggerFactory.getLogger(TimeBookingResource.class);

    private static final String ENTITY_NAME = "timeBookingServiceTimeBooking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeBookingService timeBookingService;

    private final TimeBookingRepository timeBookingRepository;

    public TimeBookingResource(TimeBookingService timeBookingService, TimeBookingRepository timeBookingRepository) {
        this.timeBookingService = timeBookingService;
        this.timeBookingRepository = timeBookingRepository;
    }

    /**
     * {@code POST  /time-bookings} : Create a new timeBooking.
     *
     * @param timeBooking the timeBooking to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeBooking, or with status {@code 400 (Bad Request)} if the timeBooking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-bookings")
    public ResponseEntity<TimeBooking> createTimeBooking(@RequestBody TimeBooking timeBooking) throws URISyntaxException {
        log.debug("REST request to save TimeBooking : {}", timeBooking);
        if (timeBooking.getId() != null) {
            throw new BadRequestAlertException("A new timeBooking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeBooking result = timeBookingService.save(timeBooking);
        return ResponseEntity
            .created(new URI("/api/time-bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /time-bookings/:id} : Updates an existing timeBooking.
     *
     * @param id the id of the timeBooking to save.
     * @param timeBooking the timeBooking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeBooking,
     * or with status {@code 400 (Bad Request)} if the timeBooking is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeBooking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-bookings/{id}")
    public ResponseEntity<TimeBooking> updateTimeBooking(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimeBooking timeBooking
    ) throws URISyntaxException {
        log.debug("REST request to update TimeBooking : {}, {}", id, timeBooking);
        if (timeBooking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timeBooking.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeBookingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TimeBooking result = timeBookingService.save(timeBooking);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeBooking.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /time-bookings/:id} : Partial updates given fields of an existing timeBooking, field will ignore if it is null
     *
     * @param id the id of the timeBooking to save.
     * @param timeBooking the timeBooking to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeBooking,
     * or with status {@code 400 (Bad Request)} if the timeBooking is not valid,
     * or with status {@code 404 (Not Found)} if the timeBooking is not found,
     * or with status {@code 500 (Internal Server Error)} if the timeBooking couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/time-bookings/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TimeBooking> partialUpdateTimeBooking(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimeBooking timeBooking
    ) throws URISyntaxException {
        log.debug("REST request to partial update TimeBooking partially : {}, {}", id, timeBooking);
        if (timeBooking.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timeBooking.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeBookingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TimeBooking> result = timeBookingService.partialUpdate(timeBooking);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, timeBooking.getId().toString())
        );
    }

    /**
     * {@code GET  /time-bookings} : get all the timeBookings.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeBookings in body.
     */
    @GetMapping("/time-bookings")
    public List<TimeBooking> getAllTimeBookings() {
        log.debug("REST request to get all TimeBookings");
        return timeBookingService.findAll();
    }

    /**
     * {@code GET  /time-bookings/:id} : get the "id" timeBooking.
     *
     * @param id the id of the timeBooking to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeBooking, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-bookings/{id}")
    public ResponseEntity<TimeBooking> getTimeBooking(@PathVariable Long id) {
        log.debug("REST request to get TimeBooking : {}", id);
        Optional<TimeBooking> timeBooking = timeBookingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeBooking);
    }

    /**
     * {@code DELETE  /time-bookings/:id} : delete the "id" timeBooking.
     *
     * @param id the id of the timeBooking to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-bookings/{id}")
    public ResponseEntity<Void> deleteTimeBooking(@PathVariable Long id) {
        log.debug("REST request to delete TimeBooking : {}", id);
        timeBookingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
