package com.example.start.hemomanager.v2.controller;
import com.example.start.hemomanager.v2.domain.Donor;
import com.example.start.hemomanager.v2.domain.Hemocenter;
import com.example.start.hemomanager.v2.domain.Schedule;
import com.example.start.hemomanager.v2.domain.ScheduleHemocenter;
import com.example.start.hemomanager.v2.dto.DonorSignInDTO;
import com.example.start.hemomanager.v2.dto.LoginDTO;
import com.example.start.hemomanager.v2.repository.DonorRepository;
import com.example.start.hemomanager.v2.repository.HemocenterRepository;
import com.example.start.hemomanager.v2.repository.ScheduleHemocenterRepository;
import com.example.start.hemomanager.v2.repository.ScheduleRepository;
import com.example.start.hemomanager.v2.request.ScheduleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
class DonorControllerTest {

    @Autowired
    private DonorController donorController;

    @MockBean
    private DonorRepository donorRepository;

    @MockBean
    private ScheduleRepository scheduleRepository;

    @MockBean
    private HemocenterRepository hemocenterRepository;

    @MockBean
    private ScheduleHemocenterRepository scheduleHemocenterRepository;


    @Test
    @DisplayName("Testar status 422 da função de 'singIn' do DonorController")
    public void testeSignIn(){

        DonorSignInDTO donorSignDTO =  new DonorSignInDTO();
        donorSignDTO.setEmail("juliacarolina@gmail.com");
        donorSignDTO.setCpf("44318783863");
        Mockito.when(donorRepository.existsByEmailAndCpf(donorSignDTO.getEmail(), donorSignDTO.getCpf())).thenReturn(true);

        ResponseEntity responseEntity = donorController.signIn(donorSignDTO);

        assertEquals(422,responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());

    }

    @Test
    @DisplayName("Testar status 200 da função de 'singIn' do DonorController")
    public void segundoTesteSignIn(){

        DonorSignInDTO donorSignDTO =  new DonorSignInDTO();
        donorSignDTO.setEmail("juliacarolina@gmail.com");
        donorSignDTO.setCpf("44318783863");

        Mockito.when(donorRepository.existsByEmailAndCpf(donorSignDTO.getEmail(), donorSignDTO.getCpf())).thenReturn(false);

        Donor donor = new Donor();
        donor.setEmail("juliacarolina@gmail.com");
        donor.setCpf("44318783863");
        Mockito.when(donorRepository.save(donor)).thenReturn(new Donor());

        ResponseEntity responseEntity = donorController.signIn(donorSignDTO);

        assertEquals(200,responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());

    }

    @Test
    @DisplayName("Testar status 404 da função 'logar' do DonorController")
    public void testeLogin() {

        LoginDTO donorDTO =  new  LoginDTO();
        donorDTO.setEmail("juliacarolina@gmail.com");
        donorDTO.setPassword("juhjuh123@");

        Mockito.when(donorRepository.findByEmailAndPassword(donorDTO.getEmail(),donorDTO.getPassword())).thenReturn(null);

        ResponseEntity responseEntity = donorController.login(donorDTO);

        assertEquals(404, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    @DisplayName("Testar status 200 da função 'logar' do DonorController")
    public void segundoTesteLogin(){

        LoginDTO donorDTO = new LoginDTO();

        donorDTO.setEmail("juliacarolina@gmail.com");
        donorDTO.setPassword("juhjuh123@");
        Mockito.when(donorRepository.findByEmailAndPassword(
                donorDTO.getEmail(),
                donorDTO.getPassword())).thenReturn(new DonorSignInDTO());

        ResponseEntity responseEntity = donorController.login(donorDTO);

        assertEquals(200,responseEntity.getStatusCodeValue());
        assertNull(responseEntity.getBody());

    }

    @Test
    @DisplayName("Testar o POST do .save Donor")
    public void testePostSave(){

        Donor donor = new Donor();

        Mockito.when(donorRepository.save(donor)).thenReturn(donor);

        assertInstanceOf(Donor.class, donorController.insertDonor(donor));
    }

    @Test
    @DisplayName("Testar o GET do countBySexMale() male donor")
    public void testeCountBySexMale(){

        Mockito.when(donorRepository.countBySexMale()).thenReturn(12L);

        assertEquals(12L,donorController.qttyMaleDonors());
    }

    @Test
    @DisplayName("Testar o GET do countBySexFemale() female donor")
    public void testeCountBySexFemale(){

        Mockito.when(donorRepository.countBySexFemale()).thenReturn(12L);

        assertEquals(12L,donorController.qttyFemaleDonors());
    }

//    @Test
//    @DisplayName("Testar o Post do Schedule ")
//    public void testeInsertSchedule(){
//
//        Schedule schedule = new Schedule();
//
//        ScheduleRequest  scheduleRequest = new  ScheduleRequest();
//
//        scheduleRequest.setDonorId(2);
//        scheduleRequest.setScheduleHemocenterId(2);
//        scheduleRequest.setHemocenterId(2);
//
//        Mockito.when(scheduleRepository.save(schedule)).thenReturn(schedule);
//
//        Mockito.when(donorRepository.findById(scheduleRequest.getDonorId())).thenReturn(Optional.of(new Donor()));
//
//        Mockito.when(scheduleHemocenterRepository.findById(scheduleRequest.getScheduleHemocenterId())).thenReturn(Optional.of(new ScheduleHemocenter()));
//
//        Mockito.when(hemocenterRepository.findById(scheduleRequest.getHemocenterId())).thenReturn(Optional.of(new Hemocenter()));
//
//        assertInstanceOf(Donor.class, donorController.insertSchedule(scheduleRequest));
//    }


}