package com.example.member.infrastructure.data.persistence.dao;

import com.example.member.infrastructure.data.persistence.po.MemberPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberDao extends JpaRepository<MemberPo, Long>, JpaSpecificationExecutor<MemberPo> {
    //    public Specification<MemberModel> buildSpec() {
//        return Specification
//                .where(hasRegisteredInXDays())
//                .and(hasStatus());
//    }
//
//    private Specification<MemberModel> hasRegisteredInXDays() {
//        return (root, query, criteriaBuilder) -> {
//            if (registeredInXDays == null) {
//                return null;
//            }
//            final Instant cutoffTime = Instant.now().minus(registeredInXDays, ChronoUnit.DAYS);
//            return criteriaBuilder.greaterThan(root.get("createTime"), cutoffTime);
//        };
//    }
//
//    private Specification<MemberModel> hasStatus() {
//        return (root, query, criteriaBuilder) -> {
//            if (status == null) {
//                return null;
//            }
//            return criteriaBuilder.equal(root.get("status"), status);
//        };
//    }
}
