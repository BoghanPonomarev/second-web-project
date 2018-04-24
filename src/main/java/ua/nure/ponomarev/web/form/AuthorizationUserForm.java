package ua.nure.ponomarev.web.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorizationUserForm {
    private String email;
    private String password;
}
