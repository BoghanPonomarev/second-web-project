package ua.nure.ponomarev.web.key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.nure.ponomarev.model.enity.Role;

/**
 * @author Bogdan_Ponamarev.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Scope("prototype")
public class AuthorizationUserKey {
    private int id;
    private Role role;
}
