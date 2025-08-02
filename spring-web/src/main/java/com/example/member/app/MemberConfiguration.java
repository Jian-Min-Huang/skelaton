package com.example.member.app;

import com.example.common.ca.adapter.EventBus;
import com.example.member.application.adapter.entity.MemberModel;
import com.example.member.application.adapter.repository.readonly.MemberReadonlyRepository;
import com.example.member.application.adapter.repository.writable.MemberWritableRepository;
import com.example.member.application.usecase.command.MemberCommandUseCase;
import com.example.member.application.usecase.query.MemberQueryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemberConfiguration {
    @Bean
    MemberCommandUseCase memberCommandUseCase(EventBus eventBus, MemberWritableRepository<MemberModel, Long> memberWritableRepository) {
        return new MemberCommandUseCase(eventBus, memberWritableRepository);
    }

    @Bean
    MemberQueryUseCase memberQueryUseCase(EventBus eventBus, MemberReadonlyRepository<MemberModel, Long> memberReadonlyRepository) {
        return new MemberQueryUseCase(eventBus, memberReadonlyRepository);
    }
}
