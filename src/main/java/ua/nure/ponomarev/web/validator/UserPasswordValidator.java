package ua.nure.ponomarev.web.validator;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Bogdan_Ponamarev.
 */
@Component
public class UserPasswordValidator{

    public  Map<String,String> validate(String password,String repeatPassword){
        Map<String,String> errors =new HashMap<>();
       if(password==null){
           errors.put("passwordEmpty","Password can`t be empty");
       }
        if(password==null){
            errors.put("repeatPasswordEmpty","Repeat password can`t be empty");
        }
        if(password!=null&&repeatPassword!=null&&!password.equals(repeatPassword)){
            errors.put("passwordsNotEquals","Password and repeatable password are`nt equals");
        }
        if(password!=null&&(password.length()>15||password.length()<6)){
            errors.put("invalidSize","Password must be from 6 to 15 symbols");
        }
        return errors;
    }
}
