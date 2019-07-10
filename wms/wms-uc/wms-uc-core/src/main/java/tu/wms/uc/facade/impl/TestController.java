package tu.wms.uc.facade.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @GetMapping(value = "test/get-session-id")
    public String getSessionId(HttpServletRequest request) {
        return "sessionId=" + request.getSession().getId();
    }

}
