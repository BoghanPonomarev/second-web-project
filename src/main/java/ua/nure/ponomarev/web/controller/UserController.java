package ua.nure.ponomarev.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.nure.ponomarev.model.enity.User;
import ua.nure.ponomarev.model.exception.MailSenderException;
import ua.nure.ponomarev.model.exception.RegistrationException;
import ua.nure.ponomarev.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Bogdan_Ponamarev.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String OUTDATE_EMAIL_TOKEN_MESSAGE = "It's already been 24 since the email was sent,\n please register again" +
            " and confirm it";
    private static final String WRONG_TOKEN_MESSAGE = "This link is wrong...";

    @Lazy
    @Autowired
    private UserService userService;

    /**
     * Default method for spring to showing refereed user form on view side
     * @return page in which form will be displayed
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showAddUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    /**
     * Request that accept user , validate they and forward to method that will send email
     * @param request - simple HttpRequest to adding new parameters that will be obtained in next method
     * @param user - user that was filled in UI side
     * @param bindingResult - spring binding result for checking validate of user
     * @return - forward to mehtod that will send email if all data correct ,
     * else - send error massages to logging page
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String showAddUserForm(HttpServletRequest request, @ModelAttribute("user") @Valid User user,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        request.setAttribute("user", user);
        return "forward:/user/email/send";
    }

    @RequestMapping(value = "/email/send", method = RequestMethod.POST)
    public String sendConfirmEmail(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        try {
            userService.sendConfirmEmail(user
                    , "http://"+request.getServerName()
                            + ":" + request.getServerPort() + "/user/email/confirm");
        } catch (MailSenderException | RegistrationException e) {
            return "SOME ERROR PAGE"; //TODO
        }
        return "redirect:SUCCESSFUL PAGE,CHECK YOUR MAIL BUCKET";
    }

    /**
     * Method that called when user follow the email confirm link
     * @param request - HttpServlet which is needed to getting token id from link
     * @param model - Spring model for filling if some exception occurs
     */
    @RequestMapping(value = "/email/confirm", method = RequestMethod.GET)
    public ModelAndView confirmEmail(HttpServletRequest request, Model model) {
        String tokenId = request.getParameter("id");
        try {
            if (tokenId != null && tokenId.chars().allMatch(Character::isDigit)) {
                userService.confirmAccount(Integer.parseInt(tokenId));
                return new ModelAndView("successEmailConfirming");
            } else {
                return new ModelAndView("successEmailConfirming"
                        ,model.addAttribute("errorMessage", WRONG_TOKEN_MESSAGE).asMap());
            }
        } catch (RegistrationException e) {
            logger.info("Registration token is old :" + e);
            return new ModelAndView("successEmailConfirming"
                    ,model.addAttribute("errorMessage", OUTDATE_EMAIL_TOKEN_MESSAGE).asMap());
        }
    }
}
