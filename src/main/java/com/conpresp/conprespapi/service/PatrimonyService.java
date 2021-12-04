package com.conpresp.conprespapi.service;

import com.conpresp.conprespapi.dto.patrimony.request.*;
import com.conpresp.conprespapi.entity.patrimony.Patrimony;
import com.conpresp.conprespapi.repository.PatrimonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatrimonyService {

    @Autowired
    private PatrimonyRepository patrimonyRepository;

    public String createPatrimony(PatrimonyCreateRequest patrimonyCreateRequest) {
        var createdPatrimony = patrimonyRepository.save(patrimonyCreateRequest.toPatrimony());
        return createdPatrimony.getId();
    }

    public Page<Patrimony> search(Specification<Patrimony> specification, Pageable pageable) {
        return patrimonyRepository.findAll(specification, pageable);
    }

    public Optional<Patrimony> getPatrimonyById(String uuid)  { return patrimonyRepository.findById(uuid); }

    public void deleteById(String uuid) { patrimonyRepository.deleteById(uuid); }

    public Patrimony updatePatrimony(String uuid, PatrimonyUpdateRequest patrimonyUpdateRequest) throws ChangeSetPersister.NotFoundException {
        var patrimony = patrimonyRepository.findById(uuid).orElseThrow(ChangeSetPersister.NotFoundException::new);

        return patrimonyRepository.save(patrimonyUpdateRequest.toPatrimony(patrimony));
    }
}
