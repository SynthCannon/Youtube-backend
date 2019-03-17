/*
 * Copyright (c) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.youtubeplaylistdata.backend.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.common.collect.Lists;
import com.youtubeplaylistdata.backend.security.Auth;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * This sample sets and retrieves localized metadata for a playlist by:
 * <p>
 * 1. Updating language of the default metadata and setting localized metadata
 * for a playlist via "playlists.update" method.
 * 2. Getting the localized metadata for a playlist in a selected language using the
 * "playlists.list" method and setting the "hl" parameter.
 * 3. Listing the localized metadata for a playlist using the "playlists.list" method
 * and including "localizations" in the "part" parameter.
 *
 * @author Ibrahim Ulukaya
 */
public class PlaylistInfoService2 {

    /**
     * Define a global instance of a YouTube object, which will be used to make
     * YouTube Data API requests.
     */
    private static YouTube youtube;
    private final static String PLAYLIST_ID = "PLK1wlrbBTSr4JVCb9t4OcuEQP0RMX1fYn";

    /**
     * Set and retrieve localized metadata for a playlist.
     *
     * @param args command line args (not used).
     */
    public static void main(String[] args) {

        // This OAuth 2.0 access scope allows for full read/write access to the
        // authenticated user's account.
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "localizations");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-localizations-sample").build();

            HashMap<String, String> parameters = new HashMap<>();
            parameters.put("part", "snippet,contentDetails");
            parameters.put("playlistId", PLAYLIST_ID);
            parameters.put("maxResults", "5");

            YouTube.PlaylistItems.List playlist = youtube.playlistItems().list(parameters.get("part"));

            if (parameters.containsKey("playlistId"))
                playlist.setPlaylistId(parameters.get("playlistId"));

            if (parameters.containsKey("maxResults"))
                playlist.setMaxResults(Long.parseLong(parameters.get("maxResults")));

            PlaylistItemListResponse response;
            List<PlaylistItem> playlistItems = new ArrayList<>();

            do {
                response = playlist.execute();

                playlist.setPageToken(response.getNextPageToken());

                playlistItems.addAll(response.getItems());

            } while (nonNull(response.getNextPageToken()));


            for (PlaylistItem item : playlistItems) {
                System.out.println(item);
            }


        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode()
                    + " : " + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }


}
