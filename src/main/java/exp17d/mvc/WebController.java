package exp17d.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
  
    @RequestMapping(value={"/","/home"})
    public String home(){
        return "home";
    }
    
    @RequestMapping(value="/home_noBootstrap_usingBasicCssOnly")
    public String home_noBootstrap_usingBasicCssOnly(){
        return "home_noBootstrap_usingBasicCssOnly";
    }
    
    @RequestMapping(value={"/welcome"})
    public String welcome(){
        return "welcome";
    }
 
    @RequestMapping(value="/public")
    public String publicPage(){
        return "public";
    }
    
    @RequestMapping(value="/anonymous")
    public String anonymous(){
        return "anonymous";
    }
    
    @RequestMapping(value="/userSettings/home4userSettings")
    public String home4userSettings(){
        return "userSettings/home4userSettings";
    }
    
    @RequestMapping(value="/authenticated")
    public String authenticated(){
        return "authenticated";
    }
    
    @RequestMapping(value="/user")
    public String user(){
        return "user";
    }
    
    @RequestMapping(value="/admin")
    public String admin(){
        return "admin";
    }
  
    @RequestMapping(value={"/login"})
    public String login(){
        return "login";
    }
  
  
    @RequestMapping(value="/403")
    public String Error403(){
        return "403";
    }
    
    
    @RequestMapping(value="/patient")
    public String patient(){
        return "patient";
    }
    
    @RequestMapping(value="/provider")
    public String provider(){
        return "provider";
    }
    
    @RequestMapping(value="/employer")
    public String employer(){
        return "employer";
    }
    
    @RequestMapping(value="/partner")
    public String partner(){
        return "partner";
    }
    
    @RequestMapping(value="/internal")
    public String internal(){
        return "internal";
    }    
}