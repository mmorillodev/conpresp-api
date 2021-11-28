package com.conpresp.conprespapi.repository;

import com.conpresp.conprespapi.DatabaseContainerInitializer;
import com.conpresp.conprespapi.entity.user.Profile;
import com.conpresp.conprespapi.entity.user.User;
import com.conpresp.conprespapi.entity.user.UserGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(initializers = {DatabaseContainerInitializer.class})
public class UserRepositoryTest {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository, ProfileRepository profileRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.groupRepository = groupRepository;
    }

    @BeforeEach
    void setup() {
        profileRepository.save(new Profile("ADMINISTRATOR"));
        groupRepository.save(new UserGroup("UAM"));
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
                groupRepository.findByName("UAM").get(),
                "Test",
                "name",
                "testmail@mail.com",
                "testpassowrd");
    }
}
