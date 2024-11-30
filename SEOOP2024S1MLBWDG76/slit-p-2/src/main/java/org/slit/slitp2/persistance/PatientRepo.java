package org.slit.slitp2.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 01:05 AM
 */

@Repository
public interface PatientRepo extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {

    Optional<Patient> findByUsername(String username);
}
