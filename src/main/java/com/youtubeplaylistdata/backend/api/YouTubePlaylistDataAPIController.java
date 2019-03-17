package com.youtubeplaylistdata.backend.api;

import com.youtubeplaylistdata.backend.client.YouTubePlaylistDataAPIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class YouTubePlaylistDataAPIController implements YouTubePlaylistDataAPI{

    @Autowired
    private YouTubePlaylistDataAPIClient client;

    @Override
    public ResponseEntity<String> getPlaylistItems() {
        return new ResponseEntity<>(client.getPlaylistData(),HttpStatus.OK);
    }
}
