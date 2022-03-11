package com.ipiecoles.java.eval.th330.repository;

import com.ipiecoles.java.eval.th330.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findAllByArtist_Id(Long id);
}
