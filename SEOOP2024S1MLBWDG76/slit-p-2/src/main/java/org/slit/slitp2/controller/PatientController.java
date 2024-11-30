package org.slit.slitp2.controller;

import lombok.RequiredArgsConstructor;
import org.slit.slitp2.persistance.PatientRepo;
import org.slit.slitp2.request.Item;
import org.slit.slitp2.request.Patient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 02:55 PM
 */

@RestController
@RequestMapping("patient")
@RequiredArgsConstructor
@Service
public class PatientController {

    private final PatientRepo patientRepo;

    @PostMapping("auth")
    public Patient auth(@RequestParam String username, @RequestParam String password) {
        org.slit.slitp2.persistance.Patient patient = patientRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("UnAuthorize"));
        if (Objects.equals(password, patient.getPassword())) {
            return patientMapper(patient);
        } else throw new RuntimeException("UnAuthorize");
    }

    @GetMapping("find")
    public Patient find(@RequestParam Long id){
        return patientMapper(findPatientById(id.intValue()));
    }

    @PostMapping
    public String save(@RequestBody Patient patient) {
        if (patient.getName() == null || patient.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (patient.getPhone() == null || patient.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        if (patient.getDob() == null) {
            throw new IllegalArgumentException("Dob cannot be null");
        }
        if (patient.getAddress() == null || patient.getAddress().isBlank()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }

        org.slit.slitp2.persistance.Patient entity = new org.slit.slitp2.persistance.Patient();
        entity.setName(patient.getName());
        entity.setPhone(patient.getPhone());
        entity.setDob(patient.getDob());
        entity.setAddress(patient.getAddress());
        entity.setUsername(patient.getUsername());
        entity.setPassword(patient.getPassword());
        patientRepo.save(entity);

        return "Saved";
    }

    @PutMapping
    public String update(@RequestBody Patient patient) {

        if (patient.getId() == null || patient.getId() < 1) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        org.slit.slitp2.persistance.Patient entity = findPatientById(patient.getId().intValue());

        if (!(patient.getName() == null || patient.getName().isBlank())) {
            entity.setName(patient.getName());
        }
        if (!(patient.getPhone() == null || patient.getPhone().isEmpty())) {
            entity.setPhone(patient.getPhone());
        }
        if (patient.getDob() != null) {
            entity.setDob(patient.getDob());
        }
        if (!(patient.getAddress() == null || patient.getAddress().isBlank())) {
            entity.setAddress(patient.getAddress());
        }


        entity.setUsername(patient.getUsername());
        entity.setPassword(patient.getPassword());

        patientRepo.save(entity);

        return "Saved";
    }

    @DeleteMapping
    public String delete(@RequestParam int id) {
        org.slit.slitp2.persistance.Patient entity = findPatientById(id);
        patientRepo.delete(entity);
        return "Deleted";
    }

    @GetMapping
    public List<Patient> findAll() {
        return patientRepo.findAll().stream().map(this::patientMapper).toList();
    }

    private Patient patientMapper(org.slit.slitp2.persistance.Patient entity) {
        Patient patient = new Patient();
        patient.setId(entity.getId());
        patient.setName(entity.getName());
        patient.setPhone(entity.getPhone());
        patient.setDob(entity.getDob());
patient.setPassword(entity.getPassword());
patient.setUsername(entity.getUsername());
        patient.setAddress(entity.getAddress());
        return patient;

    }

    private org.slit.slitp2.persistance.Patient findPatientById(int id) {
        return patientRepo.findById((long) id).orElseThrow(() -> new IllegalArgumentException("Patient not found"));
    }
}
