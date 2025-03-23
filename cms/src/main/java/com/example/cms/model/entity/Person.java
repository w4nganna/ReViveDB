package com.example.cms.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class Person {

    @Id
    @NotEmpty
    //  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment userId
    private String Id;

    @NotEmpty
    private String Name;

    @Email
    @NotEmpty
    private String email;
}
