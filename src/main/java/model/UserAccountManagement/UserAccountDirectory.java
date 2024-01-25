/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UserAccountManagement;

import java.util.ArrayList;

import model.Personnel.*;

/**
 *
 * @author kal bugrara
 */
public class UserAccountDirectory {

    ArrayList<UserAccount> userAccountList;

    public UserAccountDirectory() {

        userAccountList = new ArrayList<UserAccount>();

    }

    public UserAccount newUserAccount(Profile p, String un, String pw) {

        UserAccount ua = new UserAccount(p, un, pw);
        userAccountList.add(ua);
        return ua;
    }

    public UserAccount findUserAccount(String id) {

        for (UserAccount ua : userAccountList) {

            if (ua.isMatch(id)) {
                return ua;
            }
        }
        return null; // not found after going through the whole list
    }

    public UserAccount AuthenticateUser(String un, String pw) {

        for (UserAccount ua : userAccountList) {

            if (ua.IsValidUser(un, pw)) {
                return ua;
            }
        }
        return null; // not found after going through the whole list
    }
}
