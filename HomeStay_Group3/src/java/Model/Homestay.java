/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

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
public class Homestay {
    private int HomestayId;
    private String Name;
    private String Image;
    private String Address;
    private int Status;
    private int UserId;
    private String Description;
    private String CreateAt;
    private String UpdateAt;
    private String DeleteAt;
}
