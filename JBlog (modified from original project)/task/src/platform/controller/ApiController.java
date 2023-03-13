package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import platform.helper.CodeHelper;
import platform.model.Code;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
public class ApiController {
    private CodeHelper codeHelper;
    ApiController() {}
    @Autowired
    ApiController(CodeHelper codeHelper) {
        this.codeHelper = codeHelper;
    }

//    @RequestMapping(value = "/api/code", method = RequestMethod.GET, produces = "application/json")
    @GetMapping(value = "/api/code/{n}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object getCodeJSON(@PathVariable String n) {
        Code code = codeHelper.getCodeById(n);
        if(code == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if(code.isSecretCode()) {
            if(codeHelper.handleSecrectCode(code)) {
                codeHelper.deleteCode(n);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        return code;
    }

    @PostMapping(value = "/api/code/new", produces = "application/json")
    @ResponseBody
    public String postAddress(@RequestBody Code repCode) {
        String id = codeHelper.addNewCode(repCode.getCode(), repCode.getViews(), repCode.getTime());
        return "{\"id\": \"" + id + "\"}";
    }

    @GetMapping(value = "/api/code/latest", produces = "application/json")
    @ResponseBody
    public Object[] showLatest() {
        return codeHelper.getLatestCodeList().toArray();
    }
}
