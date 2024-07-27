
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
public class TransitionHistory {
    private int Id;
    private int WalletId;
    private String Content;
    private String CreateAt;
    private String UpdateAt;
}
