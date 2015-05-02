package com.example.cs3010.stalkerpro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Conrad on 5/2/2015.
 */
public class VideoClass {

    public UUID puuid;
    public String video_name;
    public String video_caption;
    public String created_at;
    public String modified_at;

    // make an empty profile
    public VideoClass() {
        puuid = Home.getDatabase().getUnknownUUID();
        video_name = UUID.randomUUID() + ".jpg";
        video_caption = "new image";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date();
        created_at = dateFormat.format(date);
        modified_at = dateFormat.format(date);
    }

    public VideoClass clone(VideoClass video)
    {
        VideoClass newVideo = new VideoClass();
        newVideo.video_name = video.video_name;
        newVideo.puuid = video.puuid;
        newVideo.video_caption = video.video_caption;
        newVideo.created_at = video.created_at;
        newVideo.modified_at = video.modified_at;
        return newVideo;
    }
}
