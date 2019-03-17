package com.youtubeplaylistdata.backend.client;

import com.google.api.client.auth.oauth2.Credential;
import com.google.common.collect.Lists;
import com.youtubeplaylistdata.backend.security.Auth;
import com.youtubeplaylistdata.backend.service.PlaylistInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Service
public class YouTubePlaylistDataAPIClient {

    private Credential credential;

    @Resource
    private PlaylistInfoService service;

    public YouTubePlaylistDataAPIClient(PlaylistInfoService service) {
        this.service = service;
    }

    public String getPlaylistData(){
        try {
            credential = authorize();
        } catch (IOException e) {
            return null;
        }

        return service.getPlaylistInfo(credential);
    }


    private Credential authorize() throws IOException {
        // Authorize the request.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        return Auth.authorize(scopes, "localizations");
    }
}
