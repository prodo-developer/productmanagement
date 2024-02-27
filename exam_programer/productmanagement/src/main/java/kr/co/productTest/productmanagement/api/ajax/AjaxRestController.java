package kr.co.productTest.productmanagement.api.ajax;

import kr.co.productTest.productmanagement.core.feature.Bookmark;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxRestController {

    private List<Bookmark> bookMarks = new ArrayList<>();
    
    // 즐겨찾기
    @RequestMapping(path = "/bookmark", method = RequestMethod.POST)
    public String registerBookmark(@RequestBody Bookmark bookmark) {
        bookMarks.add(bookmark);
        return "registered";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bookmarks")
    public List<Bookmark> getBookMarks() {
        return bookMarks;
    }
}
