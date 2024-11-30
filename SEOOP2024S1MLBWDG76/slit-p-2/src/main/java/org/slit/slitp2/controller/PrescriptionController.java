package org.slit.slitp2.controller;

import lombok.RequiredArgsConstructor;
import org.slit.slitp2.persistance.*;
import org.slit.slitp2.request.Prescription;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 10:51 PM
 */

@RestController
@RequestMapping("prescription")
@RequiredArgsConstructor
@Service
public class PrescriptionController {

    private final AppointmentRepo appointmentRepo;
    private final PrescriptionRepo prescriptionRepo;
    private final ItemRepo itemRepo;
    private final PrescriptionItemRepo prescriptionItemRepo;


    @GetMapping("find")
    public Prescription find(@RequestParam Long id){
        return prescriptionMap(findPrescriptionById(id));
    }

    @PostMapping
    public String save(@RequestBody Prescription prescription) {
        if (prescription.getDate() == null) {
            throw new RuntimeException("date is null");
        }
        if (prescription.getAppointment() == null) {
            throw new RuntimeException("appointment is null");
        }
        if (prescription.getAppointment().getId() == null || prescription.getAppointment().getId() <= 0) {
            throw new RuntimeException("appointment id is null");
        }
        if (prescription.getPrescriptionItems() == null || prescription.getPrescriptionItems().isEmpty()) {
            throw new RuntimeException("prescription items is null");
        }
        Appointment appointmentById = findAppointmentById(prescription.getAppointment().getId().longValue());

        org.slit.slitp2.persistance.Prescription entity = new org.slit.slitp2.persistance.Prescription();
        entity.setDate(prescription.getDate());
        entity.setAppointment(appointmentById);
        prescriptionRepo.save(entity);
        prescriptionItemRepo.saveAll(prescriptionItemsMap(prescription.getPrescriptionItems(), entity));
        return "ok";

    }

    private List<PrescriptionItem> prescriptionItemsMap(List<org.slit.slitp2.request.PrescriptionItem> prescriptionItems,
                                                        org.slit.slitp2.persistance.Prescription prescription) {
        return itemRepo.findAllById(prescriptionItems.stream().map(prescriptionItem -> {
            if (prescriptionItem.getItem() != null) {
                return prescriptionItem.getItem().getId() == null ? 0L : prescriptionItem.getItem().getId().longValue();
            } else return 0L;
        }).toList()).stream().map(item -> {
            PrescriptionItem prescriptionItem = new PrescriptionItem();
            prescriptionItem.setPrescription(prescription);
            prescriptionItem.setItem(item);
            prescriptionItems.stream().filter(prescriptionItem1 -> prescriptionItem1.getItem() != null &&
                    prescriptionItem1.getItem().getId() == item.getId()).findFirst().ifPresent(prescriptionItem1 -> {
                prescriptionItem.setDays(prescriptionItem1.getDays());
                prescriptionItem.setNote(prescriptionItem1.getNote());
            });
            return prescriptionItem;
        }).toList();
    }

    private Appointment findAppointmentById(Long appointmentId) {
        return appointmentRepo.findById(appointmentId).orElseThrow(() -> new RuntimeException("appointment not found"));
    }


    @PutMapping
    public String update(@RequestBody Prescription prescription) {

        if (prescription.getId() == null) {
            throw new RuntimeException("id is null");
        }
        org.slit.slitp2.persistance.Prescription entity = findPrescriptionById(prescription.getId().longValue());

        if (prescription.getDate() != null) {
            entity.setDate(prescription.getDate());
        }

        if (prescription.getAppointment() != null && prescription.getAppointment().getId() != null && prescription.getAppointment().getId() > 0) {
            Appointment appointmentById = findAppointmentById(prescription.getAppointment().getId().longValue());
            entity.setAppointment(appointmentById);
        }

        if (!(prescription.getPrescriptionItems() == null || prescription.getPrescriptionItems().isEmpty())) {
            prescriptionItemRepo.saveAll(entity.getPrescriptionItems().stream().peek(prescriptionItem ->
                    prescriptionItem.setDeleted(true) ).toList());
            prescriptionItemRepo.saveAll(prescriptionItemsMap(prescription.getPrescriptionItems(), entity));
        }


        prescriptionRepo.save(entity);

        return "ok";
    }

    @DeleteMapping
    public String delete(@RequestParam Long id) {
        prescriptionRepo.delete(findPrescriptionById(id));
        return "deleted";
    }

    @GetMapping
    public List<Prescription> findAll() {
        return prescriptionRepo.findAll().stream().map(this::prescriptionMap).toList();
    }

    private Prescription prescriptionMap(org.slit.slitp2.persistance.Prescription entity) {
        Prescription prescription = new Prescription();
        prescription.setId(entity.getId());
        prescription.setDate(entity.getDate());
        Appointment appointment = entity.getAppointment();
        if (appointment != null) {
            org.slit.slitp2.request.Appointment response = new org.slit.slitp2.request.Appointment();
            response.setId(appointment.getId().intValue());
            response.setReservedTime(appointment.getReservedTime());
            response.setPatient(appointment.getPatient().getId().intValue());
            response.setPatientName(appointment.getPatient().getName());
            response.setDoctor(appointment.getDoctor().getId().intValue());
            response.setDoctorName(appointment.getDoctor().getName());
            response.setAmount(appointment.getAmount());
            prescription.setAppointment(response);
        }

        List<org.slit.slitp2.request.PrescriptionItem> list = new ArrayList<>(entity.getPrescriptionItems().stream().map(
                this::mapPrescriptionItems
        ).toList());
        list.removeIf(Objects::isNull);
        prescription.setPrescriptionItems(list);
        return prescription;
    }

    private org.slit.slitp2.request.PrescriptionItem mapPrescriptionItems(PrescriptionItem prescriptionItem) {
        if (prescriptionItem.getDeleted() != null && prescriptionItem.getDeleted()) {
            return null;
        }
        org.slit.slitp2.request.PrescriptionItem response = new org.slit.slitp2.request.PrescriptionItem();
        response.setId(prescriptionItem.getId());
        Item item = prescriptionItem.getItem();
        if (item != null) {
            org.slit.slitp2.request.Item resItem = new org.slit.slitp2.request.Item();
            resItem.setId(item.getId());
            resItem.setName(item.getName());
            resItem.setPrice(item.getPrice());
            response.setItem(resItem);
        }
        response.setDays(prescriptionItem.getDays());
        response.setNote(prescriptionItem.getNote());
        return response;
    }


    private org.slit.slitp2.persistance.Prescription findPrescriptionById(Long id) {
        return prescriptionRepo.findById(id).orElseThrow(() -> new RuntimeException("Prescription Not found"));
    }
}
