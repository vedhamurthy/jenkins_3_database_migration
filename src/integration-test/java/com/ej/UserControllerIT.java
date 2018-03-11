package com.ej;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ej.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class UserControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void addUser() throws Exception {
        String url = base.toString() + "/user/new";
        User user = new User();
        user.setName("John");
        ResponseEntity<User> responseEntity = template.postForEntity(url, user, User.class);
        User newUser = responseEntity.getBody();
        assertThat(newUser.getName(), equalTo("John"));
        assertThat(newUser.getId(), notNullValue());
    }

    @Test
    public void getUser() throws Exception {
        // Add user
        String addUserUrl = base.toString() + "/user/new";
        User user = new User();
        user.setName("Louis");
        user.setEmail("louis@gmail.com");
        ResponseEntity<User> responseEntity = template.postForEntity(addUserUrl, user, User.class);
        user = responseEntity.getBody();
        //Get user
        String url = base.toString() + "/user/" + user.getId();
        responseEntity = template.getForEntity(url, User.class);
        User getUser = responseEntity.getBody();
        assertThat(getUser.getName(), equalTo("Louis"));
        assertThat(getUser.getEmail(), notNullValue());
    }
}
