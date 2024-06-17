/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Datnt
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int Id;
    private String UserName;
    private String Password;
    private String FirstName;    
    private String LastName;
    private String Phone;
    private String Email;
    private boolean IsActive;
    private Date CreateAt;
    private Date UpdateAt;
    private Date DeleteAt;
    private String Avatar;
    private boolean IsCofirm;
    private int RoleId;
}
