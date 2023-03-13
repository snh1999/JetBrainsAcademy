package platform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import platform.helper.CodeHelper;
import platform.model.Code;

@Controller
public class HtmlController {
    private CodeHelper codeHelper;

    public HtmlController() {}
    @Autowired
    public HtmlController(CodeHelper codeHelper) {
        this.codeHelper = codeHelper;
    }

    @GetMapping(value = "/code/{n}")
    public String getHtml(@PathVariable("n") String n, Model model) {
        Code code = codeHelper.getCodeById(n);
        if(code == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if(code.isSecretCode()) {
            if(codeHelper.handleSecrectCode(code)) {
                codeHelper.deleteCode(n);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }
        model.addAttribute("code", code);
        return "code";
    }
    @GetMapping(value = "/code/new")
    public String postAddress() {
        return "codePost";
    }

    @GetMapping(value = "/code/latest")
    public String showLatestHtml(Model model) {
        model.addAttribute("codes",codeHelper.getLatestCodeList());
        return "codeLatest";
    }
}
