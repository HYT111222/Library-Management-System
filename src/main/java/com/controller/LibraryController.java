package com.controller;

import com.annotation.UserLoginToken;
import com.service.LibraryService;
import com.vo.R;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/libraryInfo")
@AllArgsConstructor
public class LibraryController {
    private final LibraryService libraryInfoService;

    // 获取图书馆信息
    @GetMapping("checkLibraryInfo/{libraryId}")
    @UserLoginToken
    public R checkLibraryInfo(@RequestAttribute(value="id") String id, @PathVariable("libraryId") String libraryId){
        System.out.println("图书馆id： " + libraryId);
        return libraryInfoService.checkLibraryInfo(libraryId);
    }

}



