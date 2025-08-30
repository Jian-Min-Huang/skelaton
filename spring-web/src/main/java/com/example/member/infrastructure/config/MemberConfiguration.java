package com.example.member.infrastructure.config;

import com.example.common.ca.EventBus;
import com.example.member.application.usecase.command.MemberCommandUseCase;
import com.example.member.application.usecase.query.MemberQueryUseCase;
import com.example.member.domain.entity.Member;
import com.example.member.domain.repository.readonly.MemberReadonlyRepository;
import com.example.member.domain.repository.writable.MemberWritableRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfiguration {
    @Bean
    MemberCommandUseCase memberCommandUseCase(
            final EventBus eventBus,
            final MemberReadonlyRepository<Member, Long> memberReadonlyRepository,
            final MemberWritableRepository<Member, Long> memberWritableRepository) {
        return new MemberCommandUseCase(eventBus, memberReadonlyRepository, memberWritableRepository);
    }

    @Bean
    MemberQueryUseCase memberQueryUseCase(
            final EventBus eventBus,
            final MemberReadonlyRepository<Member, Long> memberReadonlyRepository) {
        return new MemberQueryUseCase(eventBus, memberReadonlyRepository);
    }
}
