package ua.nure.ponomarev.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Bogdan_Ponamarev.
 */
@Controller
public class TestCommand {


    @Transactional
    @RequestMapping(name = "test",value = "/test")
    public String testCommand(){

        return "test";
    }

}
