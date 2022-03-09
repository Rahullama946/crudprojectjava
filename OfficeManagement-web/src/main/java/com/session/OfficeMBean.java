package com.session;

import com.entities.User;
import com.officeManagement.sessionBeans.OfficeSessionBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author DELL
 */
@ManagedBean(name = "officeMBean")//for the errors of two ejbs we need to set the managed bean name and sest ejb bean name as below
@SessionScoped
public class OfficeMBean {

    @EJB(beanName = "officeSessionBean")
    private OfficeSessionBean session;
    private String userName;
    private String email;
    private String password;
    private String address;

    //    for all user to be displayed in the frontend we need to create a method that provides the list of user
// init() method will run anyhow no matter what so it will retrive the userlist from session bean
    private User user;
    private ArrayList<User> userList = new ArrayList<>();
    
   
    
    @PostConstruct
    public void init() {
        userList = session.retrieveAllUser();
    }

    public String redirect1() {
        return "/login.xhtml";
    }

    public String redirect2() {
        return "/index.xhtml";
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public OfficeSessionBean getSession() {
        return session;
    }

    public void setSession(OfficeSessionBean session) {
        this.session = session;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void save() {
        User us = new User();
        us.setUserName(userName);
        us.setEmail(email);
        us.setPassword(password);
        us.setAddress(address);
        session.persist(us);
        if (userList != null) {
            userList.add(us);
        } else {
            userList = new ArrayList<>();
            userList.add(us);
        }
        userName = "";
        email = "";
        password = "";
        address = "";//it is added to to empty the field in form 

    }

    public void updateClicked() {
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setAddress(address);
        session.updateObject(user);
        userName = "";
        email = "";
        password = "";
        address = "";//it is added to to empty the field in form 
//       showSuccessMessage("update successfully");
    }

    public void update(User user) {
        this.user = user;
        userName = user.getUserName();
        email = user.getEmail();
        password = user.getPassword();
        address = user.getAddress();

    }

    public void Delete(User user) {
        user.setDelflag(true);
        session.updateObject(user);
        userList.remove(user);
    }

//    public static void showSuccessMessage(String msg) {
//        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
//        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//    }
    public String login() {
        User u = session.checkerNamePass(userName, password);
        if (u == null) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "login failed", "login failed");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return null;
        } else {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successful", "Successful");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);
            return "/profile.xhtml";
        }
    }
}
