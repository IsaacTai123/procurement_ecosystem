/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ecosystem;

//import Business.Person.PersonDirectory;

import model.user.UserAccountDirectory;

//import Business.Profiles.EmployeeDirectory;
//import Business.Profiles.StudentDirectory;


/**
 *
 * @author kal bugrara
 */
public class Ecosystem {

    String name;
//    PersonDirectory persondirectory; //all people profiles regardless of the role

    private static UserAccountDirectory useraccountdirectory;
//    private static AuthorDirectory authorDirectory;
    

    // this business (libiary management system) has UserAccountDirectory
    public Ecosystem(String n) {
        name = n;

//        persondirectory = new PersonDirectory();
//        employeedirectory = new EmployeeDirectory(this);
//        studentdirectory = new StudentDirectory();

        useraccountdirectory = new UserAccountDirectory();
        

    }

//    public PersonDirectory getPersonDirectory() {
//        return persondirectory;
//    }

    public UserAccountDirectory getUserAccountDirectory() {
        return useraccountdirectory;
    }

   
    
//    public EmployeeDirectory getEmployeeDirectory() {
//        return employeedirectory;
//    }


}
