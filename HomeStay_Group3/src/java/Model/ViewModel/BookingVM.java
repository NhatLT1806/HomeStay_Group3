/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.ViewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookingVM {
    private int RequestId;
    private String CreateAt;
    private int Status;
    private String RoomName;
    private float Price;
    private int RoomId;
    private String RoomImage;
    private String BookedByUserName;
    private String BookedByEmail;
    private String BookedByPhone;   
    private String OwnerPhone;
    private String OwnerName;
    private String OwnerEmail;

}
