package com.conpresp.conprespapi.repository;

import com.conpresp.conprespapi.DatabaseContainerInitializer;
import com.conpresp.conprespapi.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = {DatabaseContainerInitializer.class})
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void saveUserAndRetrieve() {
        var savedEntity = userRepository.save(getUserEntity());
        var retrievedEntityOptional = userRepository.findById(savedEntity.getId().toString());

        Assertions.assertTrue(retrievedEntityOptional.isPresent());
        Assertions.assertEquals(retrievedEntityOptional.get().getName(), "Test name");
        Assertions.assertEquals(retrievedEntityOptional.get().getEmail(), "testmail@mail.com");
        Assertions.assertEquals(retrievedEntityOptional.get().getPassword(), "testpassowrd");
    }

    private User getUserEntity() {
        return new User("Test name", "testmail@mail.com", "testpassowrd");
    }
}
