package com.example.member.infrastructure.persistence.dao;

import com.example.member.infrastructure.persistence.po.MemberPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberDao extends JpaRepository<MemberPo, Long>, JpaSpecificationExecutor<MemberPo> {
}
