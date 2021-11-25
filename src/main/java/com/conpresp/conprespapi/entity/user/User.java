package com.conpresp.conprespapi.entity.user;

import com.conpresp.conprespapi.entity.Auditable;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class User extends Auditable implements UserDetails {

    @Id
    private String id = UUID.randomUUID().toString();

    @NonNull
    @ManyToOne
    private Profile profile;

    @NonNull
    @ManyToOne
    private UserGroup userGroup;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(profile);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status.equals(UserStatus.ACTIVE);
    }
}
