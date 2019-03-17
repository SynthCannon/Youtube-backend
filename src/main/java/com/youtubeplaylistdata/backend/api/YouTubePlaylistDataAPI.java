package com.youtubeplaylistdata.backend.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface YouTubePlaylistDataAPI {

    @RequestMapping(
            value = "/myplaylist",
            produces = {"application/json; charset=utf-8"},
            consumes = {"application/json; charset=utf-8"},
            method = RequestMethod.GET)
    ResponseEntity<String> getPlaylistItems();

}
