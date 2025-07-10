package com.tatianacarvajal.medicalsystem.infrastructure.persistence.repository.doctor;

import com.tatianacarvajal.medicalsystem.domain.entities.Doctor;
import com.tatianacarvajal.medicalsystem.domain.entities.MedicalSpecialty;
import com.tatianacarvajal.medicalsystem.domain.repository.DoctorRepository;
import com.tatianacarvajal.medicalsystem.infrastructure.mapper.DoctorMapper;
import com.tatianacarvajal.medicalsystem.infrastructure.persistence.entity.DoctorEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DoctorRepositoryImpl implements DoctorRepository {

    private final DoctorRepositoryJpa doctorRepositoryJpa;
    private final DoctorMapper doctorMapper;

    public DoctorRepositoryImpl(DoctorRepositoryJpa doctorRepositoryJpa, DoctorMapper doctorMapper) {
        this.doctorRepositoryJpa = doctorRepositoryJpa;
        this.doctorMapper = doctorMapper;
    }

    @Override
    public Doctor create(Doctor doctor) {
        DoctorEntity doctorEntity = doctorMapper.domainToEntity(doctor);
        return doctorMapper.entityToDomain(doctorRepositoryJpa.save(doctorEntity));
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return doctorRepositoryJpa.findById(id).map(doctorMapper::entityToDomain);
    }

    @Override
    public List<Doctor> findBySpecialty(MedicalSpecialty medicalSpecialty) {
        return doctorRepositoryJpa.findByMedicalSpecialty(medicalSpecialty)
                .stream()
                .map(doctorMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Doctor update(Doctor doctor) {
        DoctorEntity doctorEntity = doctorMapper.domainToEntity(doctor);
        return doctorMapper.entityToDomain(doctorRepositoryJpa.save(doctorEntity));
    }

    @Override
    public void deleteById(Long id) {
        doctorRepositoryJpa.deleteById(id);
    }
}
