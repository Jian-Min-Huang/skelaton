package com.example.skill.service;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Builder(toBuilder = true)
@Value
@With
public class Creator {
    String name;
    String email;
}
