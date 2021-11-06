package com.conpresp.conprespapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Profile implements GrantedAuthority {

    @Id
    private String id = UUID.randomUUID().toString();
    private String name;

    @Override
    public String getAuthority() {
        return null;
    }
}
