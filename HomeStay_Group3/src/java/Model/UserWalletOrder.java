
package Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserWalletOrder {
    private int Id;
    private int UserWalletId;
    private float Ammount;
    private String CreateAt;
    private int Status;

}
