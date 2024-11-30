package org.slit.slitp2.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 01:02 AM
 */

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {
}
