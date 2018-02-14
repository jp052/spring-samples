package com.plankdev.jwtsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;


@RestController
public class SecurityRestController {

    @Autowired
    SecurityService securityService;

    @ResponseBody
    @RequestMapping("/security/generate/token")
    public Map<String, Object> generateToken(@RequestParam(value="subject") String subject){

        String token = securityService.createToken(subject, (Integer.MAX_VALUE));

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", token);

        return map;
    }

    @ResponseBody
    @RequestMapping("/security/get/subject")
    public Map<String, Object> getSubject(@RequestParam(value="token") String token){

        String subject = securityService.getSubject(token);

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("result", subject);

        return map;
    }

}
