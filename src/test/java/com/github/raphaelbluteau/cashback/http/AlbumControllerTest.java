package com.github.raphaelbluteau.cashback.http;

import com.github.raphaelbluteau.cashback.converter.AlbumConverter;
import com.github.raphaelbluteau.cashback.enums.GenreEnum;
import com.github.raphaelbluteau.cashback.http.data.response.AlbumHttpResponse;
import com.github.raphaelbluteau.cashback.usecase.AlbumUseCase;
import com.github.raphaelbluteau.cashback.usecase.data.response.Album;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlbumUseCase albumUseCase;

    @MockBean
    AlbumConverter albumConverter;

    @Autowired
    Environment env;
    
    private Album album;
    private AlbumHttpResponse albumHttpResponse;

    @Before
    public void setUp() throws Exception {
    	
    	album = Album.builder()
                .id("2")
                .name("Name")
                .genre(GenreEnum.ROCK)
                .price(BigDecimal.TEN.setScale(2, RoundingMode.FLOOR))
                .artists(Collections.emptyList())
                .build();
    	
    	albumHttpResponse = AlbumHttpResponse.builder()
            	.genre(GenreEnum.ROCK)
            	.price(BigDecimal.TEN.setScale(2, RoundingMode.FLOOR))
            	.id(2L)
            	.name("Name")
            	.build();
    }

    @Test
    public void testGetAlbums() throws Exception {
    	
    	Page<Album> albumsPage = new PageImpl<Album>(Collections.singletonList(album));
    	Page<AlbumHttpResponse> responsePage = new PageImpl<>(Collections.singletonList(albumHttpResponse));
    	
    	Mockito.when(albumUseCase.getAlbumsByGenre(any(GenreEnum.class), any(Pageable.class)))
    		.thenReturn(albumsPage);
    	Mockito.when(albumConverter.toResponse(any(Page.class)))
    		.thenReturn(responsePage);

    	mockMvc.perform(get("/albums").param("genre", "ROCK"))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.content[0].name").value("Name"))
        		.andExpect(jsonPath("$.content[0].genre").value("ROCK"))
        		.andExpect(jsonPath("$.content[0].price").value("10.0"))
        		.andExpect(jsonPath("$.content[0].id").value("2"));
    }

    @Test
    public void testGetAlbum() throws Exception {

        Mockito.when(albumUseCase.findById(anyLong())).thenReturn(album);
        
        Mockito.when(albumConverter.toResponse(album))
        	.thenReturn(albumHttpResponse);

        mockMvc.perform(get("/albums/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"))
                .andExpect(jsonPath("$.genre").value("ROCK"))
                .andExpect(jsonPath("$.price").value("10.0"))
                .andExpect(jsonPath("$.id").value("2"));
    }
}