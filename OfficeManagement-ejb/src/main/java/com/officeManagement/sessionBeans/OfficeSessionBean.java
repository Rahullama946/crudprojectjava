
package com.officeManagement.sessionBeans;

import com.entities.Faculty;
import com.entities.Profile;
import com.entities.Student;
import com.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

//session bean are always kept stateless as it need not have to store the state of the data and directly
//perform operation with the database unlike managed beans which perform hold the data from the db and display it in the ui
//so we can say that the scope is kept in managed bean
@Stateless(name = "officeSessionBean")
public class OfficeSessionBean {

    @PersistenceContext(unitName = "rahul")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }
    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Object obj) {
        em.persist(obj);
    }

    public void updateObject(Object obj) {
        em.merge(obj);
    }
    public Faculty findFaculty(long id){
        return em.find(Faculty.class,id);
    }
    public ArrayList<User> retrieveAllUser() {
       Query query2=em.createQuery("SELECT u FROM User u where ifnull(u.delflag,false)=false");
       return (ArrayList<User>) query2.getResultList();
    }
    
    public ArrayList<Student> retrieveAllStudents() {
//        below query are the hbql query example
       Query query=em.createQuery("SELECT s FROM Student s where ifnull(s.delflag,false)=false");
       return (ArrayList<Student>) query.getResultList();
    }
//    returning list of faculty from faculty entity
    public  ArrayList<Faculty> retrieveFacultyType(){
        Query query3=em.createQuery("SELECT f FROM Faculty f");
        return (ArrayList<Faculty>) query3.getResultList();
    }
    public User checkerNamePass(String username,String pass){
//        below code is native query 
        String sql="select * from user u where u.`DelFlag`=false and u.`UserName`='"+username+"' and u.`password`='"+pass+"'";
        try{
        return (User)em.createNativeQuery(sql, User.class).getSingleResult();//it will return a single user from db that satisfies the sql query
        }catch(NoResultException ne){
            return null;
        }
    }
    
    //retrieve paths of image from the profile enities saved int he db
     public ArrayList<Profile> retrieveAllProfile() {
//        below query are the hbql query example
       Query query=em.createQuery("SELECT p FROM Profile p",Profile.class);
       return (ArrayList<Profile>) query.getResultList();
    }
   
}
