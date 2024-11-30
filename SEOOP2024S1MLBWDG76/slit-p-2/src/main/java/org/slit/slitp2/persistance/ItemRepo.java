package org.slit.slitp2.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author     
 * @Project slit-p-2
 * @Date 2024-05-10 10:35 PM
 */

public interface ItemRepo extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
}
