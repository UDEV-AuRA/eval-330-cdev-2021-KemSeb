package com.ipiecoles.java.eval.th330.controller;

import com.ipiecoles.java.eval.th330.model.Album;
import com.ipiecoles.java.eval.th330.model.Artist;
import com.ipiecoles.java.eval.th330.service.AlbumService;
import com.ipiecoles.java.eval.th330.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@Controller
@RequestMapping(value = "/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}"
    )
    public ModelAndView getSingleArtistById(
            @PathVariable Long id
    ) {
        ModelAndView modelAndView = new ModelAndView("detailArtist");
        modelAndView.addObject("artist", artistService.findById(id));
        return modelAndView;
    }


    /*  Retourne une liste d'artistes par rapport a leur nom  */

    @RequestMapping(
            method = RequestMethod.GET,
            value = "",
            params = "name"
    )

    public ModelAndView findAllArtistsByContainingName(
            @RequestParam String name
    ) {
        ModelAndView modelAndView = new ModelAndView("listArtists");

        modelAndView.addObject("artists", artistService.findByNameLikeIgnoreCase(name, 0, artistService.countAllArtists().intValue(), "name", "ASC"));
        return modelAndView;
    }



    // Affiche l'ensemble des artistes présent dans la base
    @RequestMapping(
            method = RequestMethod.GET,
            value = ""
    )

    public ModelAndView findAllArtists(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam String sortProperty,
            @RequestParam String sortDirection
    ) {
        Integer artistsCount = (page * size) + 1;
        ModelAndView modelAndView = new ModelAndView("listArtists");
        modelAndView.addObject("artistsCount", artistsCount);
        modelAndView.addObject("artists", artistService.findAllArtists(page, size, sortProperty, sortDirection));
        return modelAndView;
    }

    // Get page création d'artiste
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/create"
    )
    public ModelAndView getNewArtist() {
        ModelAndView modelAndView = new ModelAndView("detailArtist");
        modelAndView.addObject("artist", new Artist());
        return modelAndView;
    }

    //Création d'un nouvel artiste

    @RequestMapping(
            method = RequestMethod.POST,
            value = "",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public RedirectView createArtist(
            Artist artist
    ){
        // On vérifie si l'artiste existe déjà selon son nom
        if (artistService.existsByName(artist.getName().trim())) {
        }

        if(artist.getId() == null){
            // Création
            artistService.createArtist(artist);
        }
        //Redirection vers /artists/{id}
        return new RedirectView("/artists/" + artist.getId());
    }

    // Modification d'un artiste

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{id}",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public RedirectView updateArtist(
            Artist artist
    ) {
        if (artistService.existsById(artist.getId())) {
            artistService.updateArtist(artist.getId(), artist);
        }

        //Redirection vers /artists/{id}
        return new RedirectView("/artists/" + artist.getId());
    }



    //Supprime un artiste

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/delete/{id}"
    )
    public RedirectView deleteArtist(
            @PathVariable Long id
    ) {
        List<Album> albums = albumService.getAllAlbumsByArtistId(id);
        if (!albums.isEmpty()) {
            albums.forEach(album -> albumService.deleteAlbum(album.getId()));
        }
        artistService.deleteArtist(id);
        return new RedirectView("/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
    }
}