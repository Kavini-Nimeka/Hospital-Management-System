package org.slit.slitp2.controller;

import lombok.RequiredArgsConstructor;
import org.slit.slitp2.persistance.DoctorRepo;
import org.slit.slitp2.request.Doctor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 07:37 AM
 */

@RestController
@RequestMapping("doctor")
@RequiredArgsConstructor
@Service
public class DoctorController {

    private final DoctorRepo doctorRepo;

    @GetMapping("find")
    public Doctor byid(@RequestParam Long id) {
        return docMap(findbyid(id));
    }

    @PostMapping
    public String save(@RequestBody Doctor doctor) {
        if (doctor.getName() == null || doctor.getName().isEmpty()) {
            throw new IllegalArgumentException("Doctor name cannot be null or empty");
        }
        if (doctor.getSpecial() == null || doctor.getSpecial().isEmpty()) {
            throw new IllegalArgumentException("Doctor Special cannot be null or empty");
        }
        if (doctor.getDegree() == null || doctor.getDegree().isEmpty()) {
            throw new IllegalArgumentException("Doctor Degree cannot be null or empty");
        }
        if (doctor.getPrice() == null || doctor.getPrice() <= 0) {
            throw new IllegalArgumentException("Doctor Price cannot be null or empty");
        }

        org.slit.slitp2.persistance.Doctor entity = new org.slit.slitp2.persistance.Doctor();
        entity.setName(doctor.getName());
        entity.setSpecial(doctor.getSpecial());
        entity.setDegree(doctor.getDegree());
        entity.setPrice(doctor.getPrice());
        doctorRepo.save(entity);
        return "Saved";
    }

    @PutMapping
    public String update(@RequestBody Doctor doctor) {
        if (doctor.getId() == null || doctor.getId() < 1) {
            throw new IllegalArgumentException("Doctor id cannot be null");
        }
        org.slit.slitp2.persistance.Doctor entity = findbyid(doctor.getId());

        if (!(doctor.getName() == null || doctor.getName().isEmpty())) {
            entity.setName(doctor.getName());
        }
        if (!(doctor.getSpecial() == null || doctor.getSpecial().isEmpty())) {
            entity.setSpecial(doctor.getSpecial());
        }
        if (!(doctor.getDegree() == null || doctor.getDegree().isEmpty())) {
            entity.setDegree(doctor.getDegree());
        }
        if (!(doctor.getPrice() == null || doctor.getPrice() <= 0)) {
            entity.setPrice(doctor.getPrice());
        }
        doctorRepo.save(entity);
        return "UPDATED";
    }

    @DeleteMapping
    public String delete(@RequestParam Long id) {
        org.slit.slitp2.persistance.Doctor findbyid = findbyid(id);
        doctorRepo.delete(findbyid);
        return "DELETED";
    }

    @GetMapping
    private List<Doctor> findAll() {
        return doctorRepo.findAll().stream().map(this::docMap).toList();
    }

    private Doctor docMap(org.slit.slitp2.persistance.Doctor entity) {
        Doctor doc = new Doctor();
        doc.setId(entity.getId());
        doc.setName(entity.getName());
        doc.setSpecial(entity.getSpecial());
        doc.setDegree(entity.getDegree());
        doc.setPrice(entity.getPrice());
        return doc;
    }

    private org.slit.slitp2.persistance.Doctor findbyid(Long id) {
        return doctorRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

}
