package com.example.cs3010.stalkerpro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Conrad on 5/2/2015.
 */
public class ImageClass {

    public UUID puuid;
    public String image_name;
    public String image_caption;
    public String created_at;
    public String modified_at;

    // make an empty profile
    public ImageClass() {
        puuid = Home.getDatabase().getUnknownUUID();
        image_name = UUID.randomUUID() + ".jpg";
        image_caption = "new image";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date();
        created_at = dateFormat.format(date);
        modified_at = dateFormat.format(date);
    }

    public ImageClass clone(ImageClass image)
    {
        ImageClass newImage = new ImageClass();
        newImage.image_name = image.image_name;
        newImage.puuid = image.puuid;
        newImage.image_caption = image.image_caption;
        newImage.created_at = image.created_at;
        newImage.modified_at = image.modified_at;
        return newImage;
    }
}
