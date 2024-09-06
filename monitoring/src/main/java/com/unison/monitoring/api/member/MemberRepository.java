package com.unison.monitoring.api.member;

import com.unison.monitoring.api.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
}
