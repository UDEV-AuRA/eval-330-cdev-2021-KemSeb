package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Album;
import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.service.AlbumService;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ArtistService artistService;

    //Ajout d'un album

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/create/artist/{artistId}",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public RedirectView addAlbum(
            @PathVariable Long artistId,
            Album album
    ) {

        Artist artist = artistService.findById(artistId);
        album.setArtist(artist);
        albumService.createAlbum(album);

        return new RedirectView("/artists/" + artistId);
    }

    //Suppression d'un album

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/delete/{albumId}/artist/{artistId}"
    )
    public RedirectView deleteAlbum(
            @PathVariable Long albumId,
            @PathVariable Long artistId
    ) {
        albumService.deleteAlbum(albumId);
        return new RedirectView("/artists/" + artistId);
    }
}

