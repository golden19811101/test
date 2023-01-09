package com.fantasy.tbs.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fantasy.tbs.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TimeBookingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeBooking.class);
        TimeBooking timeBooking1 = new TimeBooking();
        timeBooking1.setId(1L);
        TimeBooking timeBooking2 = new TimeBooking();
        timeBooking2.setId(timeBooking1.getId());
        assertThat(timeBooking1).isEqualTo(timeBooking2);
        timeBooking2.setId(2L);
        assertThat(timeBooking1).isNotEqualTo(timeBooking2);
        timeBooking1.setId(null);
        assertThat(timeBooking1).isNotEqualTo(timeBooking2);
    }
}
