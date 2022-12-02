package com.example.start.hemomanager.v2.controller;

import com.example.start.hemomanager.v2.domain.Donor;
import com.example.start.hemomanager.v2.domain.Hemocenter;
import com.example.start.hemomanager.v2.domain.Schedule;
import com.example.start.hemomanager.v2.domain.ScheduleHemocenter;
import com.example.start.hemomanager.v2.repository.DonorRepository;
import com.example.start.hemomanager.v2.repository.HemocenterRepository;
import com.example.start.hemomanager.v2.repository.ScheduleHemocenterRepository;
import com.example.start.hemomanager.v2.repository.ScheduleRepository;
import com.example.start.hemomanager.v2.request.DonorFinderRequest;
import com.example.start.hemomanager.v2.request.HemocenterFinderRequest;
import com.example.start.hemomanager.v2.request.ScheduleHemocenterRequest;
import com.example.start.hemomanager.v2.request.ScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController @RequestMapping("/schedules")
public class ScheduleController {
    @Autowired private DonorRepository donorRepository;
    @Autowired private ScheduleHemocenterRepository scheduleHemocenterRepository;
    @Autowired private ScheduleRepository scheduleRepository;
    @Autowired private HemocenterRepository hemocenterRepository;
    List<Schedule> schedules = new ArrayList<>();

    @GetMapping
    public ResponseEntity<Iterable<Schedule>> getAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        if (scheduleList.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhum agendamento cadastrado.");

        return ResponseEntity.status(200).body(scheduleList);
    }

    @GetMapping("/donor/{id}")
    public ResponseEntity<List<Schedule>> findDonorById(@PathVariable int id) {
        if (donorRepository.findById(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Doador inválido ou inexistente.");

        List<Schedule> schedule = scheduleRepository.findByDonorId(id);
        return ResponseEntity.status(200).body(schedule);
    }



    @PostMapping("/hour")
    public ResponseEntity<ScheduleHemocenter> insertDateHour(@RequestBody @Valid ScheduleHemocenterRequest scheduleRequest){
        int id = scheduleRequest.getHemocenterId();
        LocalDate scheduledDate = scheduleRequest.getScheduledDate();
        LocalTime scheduledTime = scheduleRequest.getScheduledTime();
        Hemocenter hemocenter = hemocenterRepository.findById(id);

        if (hemocenter == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hemocentro inválido ou inexistente.");

        ScheduleHemocenter scheduleHemocenter = new ScheduleHemocenter(hemocenter, scheduledDate, scheduledTime);

        scheduleHemocenterRepository.save(scheduleHemocenter);
        return ResponseEntity.status(201).body(scheduleHemocenter);
    }

    @PostMapping
    public ResponseEntity<Schedule> insertSchedule(@RequestBody ScheduleRequest scheduleRequest){
        int scheduledHemocenter = scheduleRequest.getHemocenterId();
        int scheduledDonor = scheduleRequest.getDonorId();
        int scheduledRequest = scheduleRequest.getScheduleHemocenterId();

        if (scheduledHemocenter <= 0 || scheduledHemocenter > hemocenterRepository.count())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hemocentro inválido.");

        if (scheduledDonor <= 0 || scheduledDonor > donorRepository.count())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doador inválido.");

        if (scheduleRepository.existsById(scheduledRequest))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Agendamento inválido.");

        ScheduleHemocenter scheduleHemocenter = scheduleHemocenterRepository.findById(scheduledRequest);
        Hemocenter hemocenter = hemocenterRepository.findById(scheduledHemocenter);
        Donor donor = donorRepository.findById(scheduledDonor);

        Schedule schedule = new Schedule(donor, hemocenter, scheduleHemocenter);
        scheduleRepository.save(schedule);

        return ResponseEntity.status(201).body(schedule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleHemocenter> findScheduleHemocenter(@PathVariable int id) {
        ScheduleHemocenter hemocenter = scheduleHemocenterRepository.findById(id);
        if (hemocenter == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hemocentro inválido ou inexistente.");

        return ResponseEntity.status(200).body(hemocenter);
    }

    @GetMapping("/hemocenter/{id}")
    public ResponseEntity<List<Schedule>> findByHemocenterId(@PathVariable int id) {
        List<Schedule> scheduleList = scheduleRepository.findByHemocenterId(id);
        if (scheduleList.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não foram encontrados agendamentos.");

        return ResponseEntity.status(200).body(scheduleList);
    }
}
