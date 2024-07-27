
package Model;

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
public class UserNotification {
    private int Id;
    private int UserId;
    private String Title;
    private String Content;
    private String CreateAt;
    private boolean IsRead;
}
