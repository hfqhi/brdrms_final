/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joysistvi.brdrms.repo;

import com.joysistvi.brdrms.model.User;
import java.util.List;

/**
 *
 * @author Hacutina
 */
public interface UserRepo {

    public List<User> getUser();

    public List<User> getUserViewById(int userId);

    public List<User> getAllUser();

    public List<User> getPendingUser();

    public List<User> getRejectedUser();

    public boolean approveUser(int id);

    public boolean rejectUser(int id);

    public String[] verifyLogin(String username, String password);

    public boolean createUser(User user);

    public boolean createUserAdmin(User user);

    public boolean updateUserInfo(User user);

    public boolean deleteUser(int id);

    public List<User> searchUser(String searchKeyword);

    public User getUserById(int id);

    public User getPendingUserById(int id);

    public boolean isUsernameExist(String username);
}
