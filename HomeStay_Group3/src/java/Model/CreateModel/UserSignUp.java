/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.CreateModel;

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
public class UserSignUp {
    public int Id;
    public String UserName;
    public String Password;
    public String FirstName;
    public String LastName;
    public String Phone;
    public String Email;
    public boolean IsActive;
    public Date CreateAt;
    public int Role;
}
