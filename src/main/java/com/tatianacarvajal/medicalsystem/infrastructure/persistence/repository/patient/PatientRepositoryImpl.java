package com.tatianacarvajal.medicalsystem.infrastructure.persistence.repository.patient;

import com.tatianacarvajal.medicalsystem.domain.entities.Patient;
import com.tatianacarvajal.medicalsystem.domain.repository.PatientRepository;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.PatientMapper;
import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.PatientEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PatientRepositoryImpl implements PatientRepository {

    private final PatientRepositoryJpa patientRepositoryJpa;
    private final PatientMapper patientMapper;

    public PatientRepositoryImpl(PatientRepositoryJpa patientRepositoryJpa, PatientMapper patientMapper) {
        this.patientRepositoryJpa = patientRepositoryJpa;
        this.patientMapper = patientMapper;
    }

    @Override
    public Patient create(Patient patient) {
        PatientEntity patientEntity = patientMapper.domainToEntity(patient);
        return patientMapper.entityToDomain(patientRepositoryJpa.save(patientEntity));
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepositoryJpa.findById(id).map(patientMapper::entityToDomain);
    }

    @Override
    public Patient update(Patient patient) {
        PatientEntity patientEntity = patientMapper.domainToEntity(patient);
        return patientMapper.entityToDomain(patientRepositoryJpa.save(patientEntity));
    }

    @Override
    public void deleteById(Long id) {

    }
}
