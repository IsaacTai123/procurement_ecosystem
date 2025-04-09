/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

*/
package util;

//import Business.Person.Person;
//import Business.Person.PersonDirectory;
//import Business.Profiles.EmployeeDirectory;
//import Business.Profiles.EmployeeProfile;
//import Business.Profiles.StudentDirectory;
//import Business.Profiles.StudentProfile;


import enums.Role;
import model.ecosystem.Ecosystem;
import model.user.UserAccount;
import model.user.UserAccountDirectory;


/**
 *
 * @author kal bugrara
 */
public class MockDataInitializer {

    public static Ecosystem initialize() {
        Ecosystem business = new Ecosystem("Library Management System");

// Create Persons
//      PersonDirectory persondirectory = business.getPersonDirectory();
      
      
// person representing sales organization        
//        Person person001 = persondirectory.newPerson("John Smith");
//        Person person002 = persondirectory.newPerson("Gina Montana");
//        Person person003 = persondirectory.newPerson("Adam Rollen");
// 
//        Person person005 = persondirectory.newPerson("Jim Dellon");
//        Person person006 = persondirectory.newPerson("Anna Shnider");
//        Person person007 = persondirectory.newPerson("Laura Brown");
//        Person person008 = persondirectory.newPerson("Jack While");
//        Person person009 = persondirectory.newPerson("Fidelity"); //we use this as customer
        
        

// Create Admins to manage the business
//        EmployeeDirectory employeedirectory = business.getEmployeeDirectory();
//        EmployeeProfile employeeprofile0 = employeedirectory.newEmployeeProfile(person001);
//        
//        StudentDirectory studentdirectory = business.getStudentDirectory();
//        StudentProfile studentprofile0 = studentdirectory.newStudentProfile(person003);
        


   
        // Create User accounts that link to specific profiles
        // 1 admin, 3 branchManager, 3 customers
        UserAccountDirectory userAccountDirectory = business.getUserAccountDirectory();
        UserAccount account1 = userAccountDirectory.newUserAccount("a",Role.ADMIN, ""); /// order products for one of the customers and performed by a sales person
//        UserAccount account2 = userAccountDirectory.newUserAccount("b1", Role.BRANCHMANAGER, "1"); /// order products for one of the customers and performed by a sales person
//        UserAccount account3 = userAccountDirectory.newUserAccount("b2", Role.BRANCHMANAGER, "2"); /// order products for one of the customers and performed by a sales person
//        UserAccount account4 = userAccountDirectory.newUserAccount("b3", Role.BRANCHMANAGER, "3"); /// order products for one of the customers and performed by a sales person
//
//        UserAccount account5 = userAccountDirectory.newUserAccount("c1", Role.CUSTOMER, "1"); /// order products for one of the customers and performed by a sales person
//        UserAccount account6 = userAccountDirectory.newUserAccount("c2", Role.CUSTOMER, "2"); /// order products for one of the customers and performed by a sales person
//        UserAccount account7 = userAccountDirectory.newUserAccount("c3", Role.CUSTOMER, "3"); /// order products for one of the customers and performed by a sales person

        
        
        
        
        return business;

    }

}
