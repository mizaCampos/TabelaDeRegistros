package com.miza.sprimgmvc.TabelaDeRegistros.repository;

import com.miza.sprimgmvc.TabelaDeRegistros.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
