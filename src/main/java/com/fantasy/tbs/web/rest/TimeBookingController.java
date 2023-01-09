package com.fantasy.tbs.web.rest;

import com.fantasy.tbs.domain.TimeBookDTO;
import com.fantasy.tbs.domain.TimeBooking;
import com.fantasy.tbs.service.impl.TimeBookingServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TimeBookingController {

    private final TimeBookingServiceImpl timeBookingService;

    public TimeBookingController(TimeBookingServiceImpl timeBookingService) {
        this.timeBookingService = timeBookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<Void> addTimeBooking(@RequestBody TimeBookDTO timeBookDTO) {
        timeBookingService.bookTime(timeBookDTO);
        return ResponseEntity.ok().build();
    }
   // retrieve how many hours an employee worked
    @PostMapping("/findByPersonNumber/{id}")
    public Optional<TimeBooking> findByPersonNumber(@PathVariable Long id) {
       return  timeBookingService.findOne(id);
    }

    // we want that tool automatically informs when an employee forgot to book his time on the same day
    @PostMapping("/autoSendMessage")
    public void autoSendMessage() {
        List<TimeBooking> timeBookingList=timeBookingService.findAll();
        Calendar calendar = Calendar.getInstance();
        Date strDate = calendar.getTime();
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String today= fmt.format(strDate);
        for(TimeBooking timeNooking:timeBookingList){
           String date= fmt.format(timeNooking.getBooking());
           if(date.equals(today)){
               autoSendMessages(timeNooking);
           }
        }
    }
   // send message model
    private void autoSendMessages(TimeBooking timeNooking) {

    }
}
