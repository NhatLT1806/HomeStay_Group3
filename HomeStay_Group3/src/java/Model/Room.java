/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private int RoomId;
    private String Name;
    private float Price;
    private float Area;
    private int MaxParticipants;
    private int HomestayId;
    private String Image;
    private int Status;
    private List<Contract> contracts;
}
