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
public class Contract {
    private int ContractId;
    private String Name;
    private int OwnerId;
    private int UserId;
    private int RoomId;
    private int HomestayId;
    private String Content;
    private String CreateAt;
    private int Status;
    private String StartDate;
    private String EndDate;
}
