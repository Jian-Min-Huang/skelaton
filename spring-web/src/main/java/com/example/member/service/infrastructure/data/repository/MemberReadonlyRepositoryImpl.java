package com.example.member.service.infrastructure.data.repository;

import com.example.member.application.adapter.entity.MemberModel;
import com.example.member.application.adapter.repository.readonly.MemberReadonlyRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MemberReadonlyRepositoryImpl implements MemberReadonlyRepository<MemberModel, Long> {
    @Override
    public Optional<MemberModel> findById(final Long aLong) {
        return Optional.empty();
    }
}
