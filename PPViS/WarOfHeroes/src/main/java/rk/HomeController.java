package rk;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@MessageMapping("/hello")
public class HomeController {

    @MessageMapping(value = "/topic/greetings")
    String sayHello(){
        return "HELLO";
    }
}
