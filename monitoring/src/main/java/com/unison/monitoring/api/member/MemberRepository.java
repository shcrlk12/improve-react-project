package com.unison.monitoring.api.member;

import com.unison.monitoring.api.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    List<MemberEntity> findByIsActiveTrue();

    Optional<MemberEntity> findByIdAndIsActiveTrue(String id);

}
