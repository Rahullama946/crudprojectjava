/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import com.entities.Profile;
import com.entities.Student;
import com.entities.User;
import com.officeManagement.sessionBeans.OfficeSessionBean;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.imageio.ImageIO;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author DELL
 */
@ManagedBean
@RequestScoped
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
            System.out.println("destination" + destination);

            Profile profile = new Profile();
            profile.setProfileName(s);
//            bus.setImgName(s);

            in.close();
            out.flush();
            out.close();

            BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            img.createGraphics().drawImage(ImageIO.read(new File(destination + s)).getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(img, "jpg", new File(destination + "thumb_" + s));

            profile.setImgNameThumbnail("thumb_" + s);
            session.persist(profile);
//            if (profileList != null) {
//                profileList.add(profile);
////            System.out.println(" added student");
//            } else {
//                profileList = new ArrayList<>();
//                profileList.add(profile);
//            }
//            System.out.println("saved successful");

        } catch (IOException ex) {
//            Logger.getLogger(VehicleMisManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public ProfileManageBean() {
    }

}
