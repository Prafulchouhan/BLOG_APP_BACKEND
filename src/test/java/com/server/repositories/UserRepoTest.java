package com.server.repositories;

import com.server.entities.User;
import com.server.exceptions.ResourceNotFoundException;
import com.server.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(
//        classes = { UserJdbcConfig.class },
//        loader = AnnotationConfigContextLoader.class)
//@Transactional
//@Configuration
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Test
    void findByEmail() {
        User user=getUser();
        userRepo.save(user);
        User result =userRepo.findByEmail(user.getEmail()).orElseThrow(()->new ResourceNotFoundException("","",0));
        assertEquals(user.getId(),result.getId());
//        System.out.println(userRepo.findById(user.getId()).get().getClass());
//        Assert.assertEquals(user.getId(),userRepo.findByEmail(user.getEmail()).get().getId());
    }

    @Test
    public void testFindById() {
        User user = getUser();
        userRepo.save(user);
        User result = userRepo.findById(user.getId()).get();
        assertEquals(user.getId(), result.getId());
    }
    @Test
    public void testFindAll() {
        User user=getUser();
        userRepo.save(user);
        List<User> result = new ArrayList<>();
        userRepo.findAll().forEach(e -> result.add(e));
        assertEquals(result.size(), 1);
    }
    @Test
    public void testSave() {
        User user=getUser();
        userRepo.save(user);
        User found = userRepo.findById(user.getId()).orElseThrow(()->new ResourceNotFoundException("user","id",user.getId()));
        assertEquals(user.getId(), found.getId());
    }
    @Test
    public void testDeleteById() {
        User user=getUser();
        userRepo.save(user);
        userRepo.deleteById(user.getId());
        List<User> result = new ArrayList<>();
        userRepo.findAll().forEach(e -> result.add(e));
        assertEquals(result.size(), 0);
    }

    User getUser(){
        return User.builder().posts(null).id(1).name("praful")
                .email("praful@gmail.com").about("about").password("xyz")
                .build();
    }
}