package com.urida.likeboard.controller;

import com.urida.likeboard.dto.LikeBoardRequestDto;
import com.urida.likeboard.service.LikeBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board/like")
public class LikeBoardController {

    private final LikeBoardService likeBoardService;

    @PostMapping("")
    public boolean clickLike(@RequestBody LikeBoardRequestDto likeBoardRequestDto) {
        Long uid = likeBoardRequestDto.getUid();
        Long board_id = likeBoardRequestDto.getBoard_id();

        return likeBoardService.clickLike(uid, board_id);
    }
}
