package com.conpresp.conprespapi.repository;

import com.conpresp.conprespapi.DatabaseContainerInitializer;
import com.conpresp.conprespapi.entity.Profile;
import com.conpresp.conprespapi.entity.User;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(initializers = {DatabaseContainerInitializer.class})
public class UserRepositoryTest {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @BeforeEach
    void setup() {
        profileRepository.save(new Profile("ADMINISTRATOR"));
    }

    @Test
    public void saveUserAndRetrieve() {
        var savedEntity = userRepository.save(getUserEntity());
        var retrievedEntityOptional = userRepository.findById(savedEntity.getId());

        assertTrue(retrievedEntityOptional.isPresent());
        assertEquals(retrievedEntityOptional.get().getFirstName(), "Test");
        assertEquals(retrievedEntityOptional.get().getLastName(), "name");
        assertEquals(retrievedEntityOptional.get().getEmail(), "testmail@mail.com");
        assertEquals(retrievedEntityOptional.get().getPassword(), "testpassowrd");
    }

    private User getUserEntity() {
        return new User(
                profileRepository.findByName("ADMINISTRATOR").get(),
                "Test",
                "name",
                "testmail@mail.com",
                "testpassowrd");
    }
}
