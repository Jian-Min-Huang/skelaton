package com.example.member.presentation.ipc.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyMemberStatusRequest {
    private Long id;
    private String email;
}
