package org.slit.slitp2.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slit.slitp2.persistance.*;
import org.slit.slitp2.request.Appointment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-09 09:14 PM
 */

@RestController
@RequestMapping("appointment")
@Service
@RequiredArgsConstructor
public class AppointmentController {


    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final AppointmentRepo appointmentRepo;


    @GetMapping("find")
    public Appointment find(@RequestParam Long id){
        return appointmentMapper(findAppointmentById(id));
    }

    @PostMapping
    public String save(@RequestBody Appointment appointment) {

        // validation
        if (appointment.getReservedTime() == null) {
            throw new RuntimeException("Reserved time is null");
        } else if (appointment.getDoctor() == null || appointment.getDoctor() <= 0) {
            throw new RuntimeException("Doctor is null");
        } else if (appointment.getPatient() == null || appointment.getPatient() <= 0) {
            throw new RuntimeException("Patient is null");
        }
        org.slit.slitp2.persistance.Appointment entity = new org.slit.slitp2.persistance.Appointment();
        entity.setDoctor(findDoctorById(appointment.getDoctor()));
        entity.setPatient(findPatientById(appointment.getPatient()));
        entity.setStatus("OPEN");
        entity.setReservedTime(appointment.getReservedTime());
        entity.setAmount(entity.getDoctor().getPrice());
        appointmentRepo.save(entity);
        return "saved appointment";
    }

    @GetMapping
    public List<Appointment> findAll() {
        return appointmentRepo.findAll().stream().map(this::appointmentMapper).toList();
    }

    private Appointment appointmentMapper(org.slit.slitp2.persistance.Appointment appointment) {
        Appointment appointment1 = new Appointment();
        appointment1.setId(appointment.getId().intValue());
        appointment1.setReservedTime(appointment.getReservedTime());
        appointment1.setPatient(appointment.getPatient().getId().intValue());
        appointment1.setPatientName(appointment.getPatient().getName());
        appointment1.setDoctor(appointment.getDoctor().getId().intValue());
        appointment1.setDoctorName(appointment.getDoctor().getName());
        appointment1.setAmount(appointment.getAmount());
        return appointment1;
    }

    @PutMapping
    public String updateAsRejectOrAccept(@RequestParam Long id, @RequestParam Boolean accept) {
        if (id == null || id <= 0) {
            throw new RuntimeException("ID is null");
        }
        org.slit.slitp2.persistance.Appointment appointmentById = findAppointmentById(id);
        appointmentById.setStatus(accept ? "ACCEPTED" : "REJECTED");
        appointmentRepo.save(appointmentById);
        return "updated appointment";
    }

    @DeleteMapping
    public String delete(@RequestParam Long id) {
        org.slit.slitp2.persistance.Appointment appointmentById = findAppointmentById(id);
        appointmentRepo.delete(appointmentById);
        return "deleted appointment";
    }

    private Doctor findDoctorById(int id) {
        return doctorRepo.findById((long) id).orElseThrow(() -> new RuntimeException("Doctor Not Found"));
    }

    private org.slit.slitp2.persistance.Appointment findAppointmentById(Long id) {
        return appointmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Appointment Not Found"));
    }

    private Patient findPatientById(int id) {
        return patientRepo.findById((long) id).orElseThrow(() -> new RuntimeException("Patient Not Found"));
    }
}
