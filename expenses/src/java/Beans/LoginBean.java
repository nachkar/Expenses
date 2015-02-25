/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author noelachkar
 */
@ManagedBean (name = "LoginBean")
@SessionScoped

public class LoginBean {
    
    /**
     * Creates a new instance of login_bean
     */
    private String username;
    private String password;
    private String dbusername;
    private String dbpassword;
    private String flag;
    private String error;
    private int userId;

    public int getUserId() {
        return userId;
    }
    
    public String getError() {
        return error;
    }
    
    public String getFlag() {
        return flag;
    }
 
    public String getDbpassword() {
        return dbpassword;
    }
    
    public String getDbusername() {
        return dbusername;
    }
 
    Connection con;
    Statement ps;
    ResultSet rs;
    String SQL_Str;
 
    public void dbData(String UName)
    {
        try
        {            
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:8889/Expenses","root","root");
            ps = con.createStatement();
            SQL_Str="Select * from users where username like ('" + UName +"')";
            rs=ps.executeQuery(SQL_Str);
            rs.next();
            dbusername=rs.getString(3);
            dbpassword=rs.getString(4);
            flag=rs.getString(2);
            userId=rs.getInt(1);
        }
        catch(ClassNotFoundException | SQLException ex)
        {
            System.out.println("Exception Occur :" + ex);
        }
    }
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
    public String checkValidUser()
    {
        dbData(username);
 
        if(username.equalsIgnoreCase(dbusername))
        {
            if(password.equals(dbpassword))
            {
                FacesContext context = FacesContext.getCurrentInstance();
                switch (flag) {
                    case "0":
                        context.getExternalContext().getSessionMap().put("flag", "0");
                        context.getExternalContext().getSessionMap().put("userId",userId);
                        return "operations/List?faces-redirect=true";
                    case "1":
                        context.getExternalContext().getSessionMap().put("flag","1");
                        context.getExternalContext().getSessionMap().put("userId",userId);
                        return "operations/List?faces-redirect=true";
                }
            }
            else
            {
                error = "Your Password is incorrect.";
                return null;
            }
        }
        else
        {
            error = "Your Username doesn't exist.";
            return null;
        }
        return null;
    }
}
