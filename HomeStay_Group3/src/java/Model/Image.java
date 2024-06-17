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
public class Image {
    private int ImageId;
    private int RoomId;
    private int HomestayId;
    private String Image;
    private String CreateAt;
    private String UpdateAt;
}
