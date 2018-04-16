package ua.nure.ponomarev.web.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.nure.ponomarev.dao.UserDao;
import ua.nure.ponomarev.enity.User;
import ua.nure.ponomarev.service.UserService;

import java.util.GregorianCalendar;

/**
 * @author Bogdan_Ponamarev.
 */
@Controller
public class TestCommand {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Transactional
    @RequestMapping(name = "test",value = "/test")
    public String testCommand(){
        User user = User.builder().email("asdasd@asd.com").dateOfBirth(new GregorianCalendar(1998,11,27))
                .password("awsdasd").phoneNumber("1123451141").build();
            int id = userDao.insertUser(user);
            try {
                userService.method();
            }catch (Exception ex){
                System.out.println(ex);
            }
        return "test";
    }

}
