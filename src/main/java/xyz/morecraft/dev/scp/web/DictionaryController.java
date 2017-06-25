package xyz.morecraft.dev.scp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.morecraft.dev.scp.service.dictionary.DictionaryService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/dictionary")
    public List<?> getDict(@RequestParam("name") String name) {
        return dictionaryService.loadDictionary(name);
    }

}
