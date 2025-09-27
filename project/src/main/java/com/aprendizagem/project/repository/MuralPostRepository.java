package com.aprendizagem.project.repository;

import com.aprendizagem.project.model.MuralPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MuralPostRepository extends JpaRepository<MuralPost, Long> {
    List<MuralPost> findTop50ByOrderByCriadoEmDesc();
}
