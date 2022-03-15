/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import antlr.StringUtils;
import com.entities.Profile;
import com.mysql.jdbc.Util;
import com.officeManagement.sessionBeans.OfficeSessionBean;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author DELL
 */
@ManagedBean
@SessionScoped
public class ProfileManageBean {

    @EJB(beanName = "officeSessionBean")
    private OfficeSessionBean session;

    //    private List<Profile> list;
    //    private DataModel<Profile> dataModel;
    //     //this is the file provided by org.apache.myfaces.custom.fileupload.UploadedFile used for the multipartconfig data
    //    private UploadedFile uploadedFile;
    //    
    //    public void createProfile() throws IOException{
    //        profile.setId(0L);
    //        byte[] file=uploadedFile.;
    //        profile.setProfile(file);
    //        session.persist(profile);
    //    }
    //    public void loadTable(){
    //        dataModel=new ListDataModel<Profile>();
    //        dataModel.setWrappedData(session.findStatus(profile));
    //    }
    //    public String view(){
    //        profile=dataModel.getRowData();
    //        return "profile.xhtml";
    //    }
    //    
    //    public OfficeSessionBean getSession(){
    //        return session;
    //    }
    //
    //    public void setSession(OfficeSessionBean session) {
    //        this.session = session;
    //    }
    //
    //    public Profile getProfile() {
    //        return profile;
    //    }
    //
    //    public void setProfile(Profile profile) {
    //        this.profile = profile;
    //    }
    //
    //    public List<Profile> getList() {
    //        return list;
    //    }
    //
    //    public void setList(List<Profile> list) {
    //        this.list = list;
    //    }
    //
    //    public DataModel<Profile> getDataModel() {
    //        return dataModel;
    //    }
    //
    //    public void setDataModel(DataModel<Profile> dataModel) {
    //        this.dataModel = dataModel;
    //    }
    //
    //    public UploadedFile getUploadedFile() {
    //        return uploadedFile;
    //    }
    //
    ////    setter and getter for all the above fields
    //    public void setUploadedFile(UploadedFile uploadedFile) {
    //        this.uploadedFile = uploadedFile;
    //    }
    private List<Profile> profileList;

    @PostConstruct
    public void init() {
        profileList = session.retrieveAllProfile();
    }

    public void profileImageUploadHandler1(FileUploadEvent event) {

        profileImageUploadHandler(event.getFile());
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "uploaded Successful", "uploaded Successful");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public void profileImageUploadHandler(UploadedFile file) {
//        System.out.println("called.");
        if (file == null) {
            return;
        }
        String destination = "C:\\Users\\DELL\\Documents\\images\\";

        String s = file.getFileName();
        s = s.replace(s.substring(s.lastIndexOf(".")), "_" + new Date().getTime() + s.substring(s.lastIndexOf(".")));
        try {
            InputStream in = file.getInputstream();
            OutputStream out = new FileOutputStream(new File(destination + s));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
//            System.out.println("destination" + destination);

            Profile profile = new Profile();
            profile.setProfileName(s);

            in.close();
            out.flush();
            out.close();

            BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            img.createGraphics().drawImage(ImageIO.read(new File(destination + s)).getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(img, "jpg", new File(destination + "thumb_" + s));

            profile.setImgNameThumbnail("thumb_" + s);
            session.persist(profile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public List<Profile> getFilePath() {
        return profileList;
    }

    public OfficeSessionBean getSession() {
        return session;
    }

    public void setSession(OfficeSessionBean session) {
        this.session = session;
    }

    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }

    //    this string is used to save the profileimageName 
    private String fileName;

    public void triggerdialog(Profile profile) {
        fileName = profile.getProfileName();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        System.out.println("file " + fileName);
        return fileName;
    }

//    private StreamedContent file;
//
//    public void downloadFile() {
//        
//        InputStream stream=this.getClass().getResourceAsStream(file.);
//        System.out.println("stream "+stream.toString());
//        file = new DefaultStreamedContent(stream, "image/jpeg", fileName);
//    }
//
//    public StreamedContent getFile() {
//        return this.file;
//    }
//
    public StreamedContent getDownloadFile() throws FileNotFoundException {
        String fileName2 = getFileName();
        System.out.println("str" + fileName2);
        String s = "C:\\Users\\DELL\\Documents\\images\\";

        File file = new File(s, fileName2);
        return new DefaultStreamedContent(new FileInputStream(file), "image/jpeg", file.getName());
    }

    public ProfileManageBean() {
    }

}
