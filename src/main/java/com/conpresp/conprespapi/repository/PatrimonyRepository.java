package com.conpresp.conprespapi.repository;

import com.conpresp.conprespapi.entity.patrimony.Patrimony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatrimonyRepository extends JpaRepository<Patrimony, String>, JpaSpecificationExecutor<Patrimony> {

}
