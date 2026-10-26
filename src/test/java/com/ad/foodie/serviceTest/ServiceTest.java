package com.ad.foodie.serviceTest;

import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;

import com.ad.foodie.entity.Restaurant;
import com.ad.foodie.entity.Role;
import com.ad.foodie.entity.RoleEntity;
import com.ad.foodie.entity.User;
import com.ad.foodie.service.UserService;

@SpringBootTest
public class ServiceTest {

    UserService us;

    @Autowired
    public ServiceTest(UserService us) {
        this.us = us;
    }

    ///one to many mapping
    @Test
    public void testSave() {

        LocalTime openTime = LocalTime.of(9, 0);
        ///LocalTime.of(int hour(0-23),int minutes(0-59),int second(0-59));
        LocalTime closeTime = LocalTime.of(23, 0);

        //user data
        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setName("Aditya Verma");
        user.setEmail("adityaTest@gmail.com");
        user.setPassword("abc");
        user.setAddress("Test add, Delhi");
        user.setPhoneNumber("1234554321");
        user.setRoles(Role.ADMIN);

        ////restaurants data
        Restaurant res = new Restaurant();

        res.setId(UUID.randomUUID().toString());
        res.setName("KFC");
        res.setAddress("Delhi");
        res.setOpenTime(openTime);
        res.setCloseTime(closeTime);

        Restaurant res1 = new Restaurant();

        res1.setId(UUID.randomUUID().toString());
        res1.setName("MacD");
        res1.setAddress("Delhi");
        res1.setOpenTime(openTime);
        res1.setCloseTime(closeTime);

        ///setting user into restaurant-one to many
        res.setUser(user);
        res1.setUser(user);

        ///adding restaurant into user restaurant list --many to one
        user.getRestaurants().add(res);
        user.getRestaurants().add(res1);

        User save = us.saveUser(user);
        System.out.println(save.getName());

    }

    @Test
    public void testUpdate() {

        User newUser = new User();  //name,email,address

        newUser.setName("Aditya");
        newUser.setEmail("abcTest@gamil.com");
        newUser.setAddress("test,Old Delhi");

        Restaurant newRes = new Restaurant(); //name ,address
        newRes.setName("StarBucks");
        newRes.setAddress("Delhi");

        User update = us.updateNameAndEmail(newUser, "6821784b-cac3-445f-8936-0d1ad9ac5a14", newRes, "c47cba98-3c9f-4267-8e7f-7f3f736a6a3a");
        System.out.println(update.getName());

    }

    // /WE Use only the owning side--USER in the test:
    @Test
    public void testSaveRole() {

        //user data
        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setName("Aadi");
        user.setEmail("aadiTest@gmail.com");
        user.setPassword("xyz");
        user.setAddress("Test add, New Delhi");
        user.setPhoneNumber("1234554321");
        user.setRoles(Role.CUSTOMER);

        User user1 = new User();

        user1.setId(UUID.randomUUID().toString());
        user1.setName("Jhon");
        user1.setEmail("johnTest@gmail.com");
        user1.setPassword("pqr");
        user1.setAddress("Test add, Noida , UP");
        user1.setPhoneNumber("1234554321");
        user1.setRoles(Role.DELIVERY_BOY);

        ///roleEntity data
        RoleEntity role = new RoleEntity();
        role.setName("Role_ADMIN");

        RoleEntity role1 = new RoleEntity();
        role1.setName("Role_Guest");

        ///Link
        // role.getUsers().add(user);
        // role1.getUsers().add(user);
        // role1.getUsers().add(user1);

        //User is the owning side: //RoleEntity is the inverse side:
        //adding users to both sides before saving:
        // Because each User already has a manually assigned UUID, 
        //Spring Data uses merge(). Hibernate follows RoleEntity.users during the
         //merge and tries to find those users in the database. 
         //They are not inserted yet, so it throws:No row with the given identifier exists for entity User


         //
        //Use only the owning side in the test:

        user.getRoleEntities().add(role);
        user.getRoleEntities().add(role1);
        user1.getRoleEntities().add(role1);

        ///users list
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);

        List<User> saveList = us.saveUserRole(users);
        for (User ele : saveList) {
            System.out.println(ele.getName());
            for (RoleEntity r : ele.getRoleEntities()) {
                System.out.println(r.getName());
            }
        }

    }

    //FOR AVOIDING ERROR :No row with the given identifier exists for entity User
    // Implemented save-first flow in 
    @Test
    public void testSaveRole1() {

        //user data
        User user = new User();

        user.setId(UUID.randomUUID().toString());
        user.setName("Aadi");
        user.setEmail("aadiTest@gmail.com");
        user.setPassword("xyz");
        user.setAddress("Test add, New Delhi");
        user.setPhoneNumber("1234554321");
        user.setRoles(Role.CUSTOMER);

        User user1 = new User();

        user1.setId(UUID.randomUUID().toString());
        user1.setName("Jhon");
        user1.setEmail("johnTest@gmail.com");
        user1.setPassword("pqr");
        user1.setAddress("Test add, Noida , UP");
        user1.setPhoneNumber("1234554321");
        user1.setRoles(Role.DELIVERY_BOY);

        ///roleEntity data
        RoleEntity role = new RoleEntity();
        role.setName("Role_ADMIN");

        RoleEntity role1 = new RoleEntity();
        role1.setName("Role_Guest");

        // ///Link
        // // role.getUsers().add(user);
        // // role1.getUsers().add(user);
        // // role1.getUsers().add(user1);
        // //User is the owning side: //RoleEntity is the inverse side:
        // //adding users to both sides before saving:
        // // Because each User already has a manually assigned UUID, 
        // //Spring Data uses merge(). Hibernate follows RoleEntity.users during the
        //  //merge and tries to find those users in the database. 
        //  //They are not inserted yet, so it throws:No row with the given identifier exists for entity User
        //  //
        // //WE Use only the owning side in the test:
        // user.getRoleEntities().add(role);
        // user.getRoleEntities().add(role1);
        // user1.getRoleEntities().add(role1);
        // /////////////////////////////////////////////////////////////////
        // Users are saved first.
        // Roles are added afterward.
        // Users are saved again with the role relationships.
        // Only User.roleEntities is updated because it owns the relationship.
        ///users list
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);

        List<User> savedUsers = us.saveUserRole(users);

        User savedUser = savedUsers.get(0);
        User savedUser1 = savedUsers.get(1);

        savedUser.getRoleEntities().add(role);
        savedUser.getRoleEntities().add(role1);
        savedUser1.getRoleEntities().add(role1);

        List<User> saveList = us.saveUserRole(savedUsers);

        for (User ele : saveList) {
            System.out.println(ele.getName());
            for (RoleEntity r : ele.getRoleEntities()) {
                System.out.println(r.getName());
            }
        }

    }
}
