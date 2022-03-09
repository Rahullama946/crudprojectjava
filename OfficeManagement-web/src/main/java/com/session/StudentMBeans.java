/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import com.entities.Faculty;
import com.entities.Student;
import com.officeManagement.sessionBeans.OfficeSessionBean;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "studentMBeans")
@SessionScoped
public class StudentMBeans { 

    @EJB(beanName = "officeSessionBean")
//            a session is created for each student each time which we can manipulate to save delete throught the help of sessionbeans
    OfficeSessionBean session;
//    private String studentName;
//    private String rollNo;
//    private String subject;
//    private String classes;

    private Student students;
    private ArrayList<Student> studentList;

//    retrieving arraylist of faculty entity
    private ArrayList<Faculty> facultyList;

    
    public ArrayList<Faculty> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(ArrayList<Faculty> facultyList) {
        this.facultyList = facultyList;
    }

    @PostConstruct
    public void init() {
        students = new Student();
        facultyList = session.retrieveFacultyType();
        studentList = session.retrieveAllStudents();
    }

    public String redirect() {
        students = new Student();
        tableflag = false;
        return "/signup.xhtml";
    }
    

    public void ajaxCalled() {
        System.out.println("students.getFaculty() :" + students.getFaculty());
        if (students.getFaculty() != null) {
            System.out.println("faculty :" + students.getFaculty().getFacultyName());
        }
    }

    public OfficeSessionBean getSession() {
        return session;
    }

    public void setSession(OfficeSessionBean session) {
        this.session = session;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }

    public Student getStudents() {
        return students;
    }

    public void setStudents(Student students) {
        this.students = students;
    }

//    public String getStudentName() {
//        return studentName;
//    }
//
////    getter and setter
//    public void setStudentName(String studentName) {
//        this.studentName = studentName;
//    }
//
//    public String getRollNo() {
//        return rollNo;
//    }
//
//    public void setRollNo(String rollNo) {
//        this.rollNo = rollNo;
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public void setSubject(String subject) {
//        this.subject = subject;
//    }
//
//    public String getClasses() {
//        return classes;
//    }
//
//    public void setClasses(String classes) {
//        this.classes = classes;
//    }
    private Boolean tableflag;

    public Boolean getTableflag() {
        return tableflag;
    }

    public void setTableflag(Boolean tableflag) {
        this.tableflag = tableflag;
    }

    public void addStudent() {
//        Student newStudent = new Student();
//        
//        newStudent.setStudentName();
//        newStudent.setRollNo();
//        newStudent.setSubject();
//        newStudent.setClasses();
//here we simply persiste the student object ko instance created above 
//it will automatically hold the data of student in Student entity

        session.persist(students);
        if (studentList != null) {
            studentList.add(students);
//            System.out.println(" added student");
        } else {
            studentList = new ArrayList<>();
            studentList.add(students);

        }
        tableflag = true;
//        to make the field of the form empty 
        students = new Student();
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Save Successful", "Save Successful");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public void updateClicked() {
//        students.setStudentName(studentName);
//        students.setRollNo(rollNo);
//        students.setSubject(subject);
//        students.setClasses(classes);
        session.updateObject(students);
//        to make field empty after clicking the button
//        studentName = "";
//        rollNo = "";
//        subject = "";
//        classes = "";
        students = new Student();

    }

    public void update(Student students) {
        this.students = students;

//     studentName = student.getStudentName();
//        rollNo = student.getRollNo();
//        subject = student.getSubject();
//        classes = student.getClasses();
    }

    public void delete(Student student) {
        student.setDelflag(true);
        session.updateObject(student);
        studentList.remove(student);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deletion Successful", "Deletion Successful");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    

//    boolean value to be passed on the rendered property of signup.xhtml page
}
