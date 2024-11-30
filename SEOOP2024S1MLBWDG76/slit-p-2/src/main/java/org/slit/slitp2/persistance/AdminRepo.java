package org.slit.slitp2.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-11 11:29 AM
 */
@Repository
public interface AdminRepo extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
    Optional<Admin> findByUsername(String username);
}
