package rk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.RequireJS;

@RestController
public class RequireJSController {
    @RequestMapping(value = "/webjarsjs", produces = "application/javascript")
    public String webJars() {
        return RequireJS.getSetupJavaScript("/webjars/");
    }
}
