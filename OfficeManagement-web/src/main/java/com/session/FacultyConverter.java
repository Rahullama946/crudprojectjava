/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.session;

import com.entities.Faculty;
import com.officeManagement.sessionBeans.OfficeSessionBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author DELL
 */
@ManagedBean
@RequestScoped
@FacesConverter(value = "facultyConverter")
public class FacultyConverter implements Converter {

    @EJB(beanName = "officeSessionBean")
    private OfficeSessionBean officeSessionBean;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("value :" + value);
        if (value == null || "".equals(value) || "Select one".equals(value)) {
            return null;
        }
        return officeSessionBean.findFaculty(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value == "" || "Select one".equals(value)) {
            return null;
        }
        Faculty au = (Faculty) value;
        return String.valueOf(au.getId());
    }

}
