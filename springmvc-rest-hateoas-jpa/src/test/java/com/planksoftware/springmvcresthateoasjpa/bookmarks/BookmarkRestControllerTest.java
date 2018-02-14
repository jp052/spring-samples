package com.planksoftware.springmvcresthateoasjpa.bookmarks;

import com.planksoftware.springmvcresthateoasjpa.SpringmvcRestHateoasJpaApplication;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringmvcRestHateoasJpaApplication.class)
@WebAppConfiguration
public class BookmarkRestControllerTest {

    private MediaType contentTypeHyperMedia = new MediaType(MediaType.APPLICATION_JSON.getType(), "hal+json", Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "bdussault";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Account account;

    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        //TODO: check if best assert
        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.bookmarkRepository.deleteAllInBatch();
        this.accountRepository.deleteAllInBatch();

        this.account = accountRepository.save(new Account(userName, "password"));
        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + userName, "A description 1")));
        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + userName, "A description 2")));
    }

    @Ignore
    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/george/bookmarks/")
                .content(this.json(new Bookmark(null, null, null)))
                .contentType(contentTypeHyperMedia))
                    .andExpect(status().isNotFound()
                    );
    }

    @Ignore
    @Test
    public void readSingleBookmark() throws Exception {
        mockMvc.perform(get("/" + userName + "/bookmarks/"
                + this.bookmarkList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeHyperMedia))
                .andExpect(jsonPath("$.bookmark.id", is(this.bookmarkList.get(0).getId().intValue())))
                .andExpect(jsonPath("$.bookmark.uri", is("http://bookmark.com/1/" + userName)))
                .andExpect(jsonPath("$.bookmark.description", is("A description 1")));

    }

    @Ignore
    @Test
    public void readBookmarks() throws Exception {
        mockMvc.perform(get("/" + userName + "/bookmarks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeHyperMedia))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[0].bookmark.id", is(this.bookmarkList.get(0).getId().intValue())))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[0].bookmark.uri" ,is("http://bookmark.com/1/" + userName)))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[0].bookmark.description", is("A description 1")))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[1].bookmark.id", is(this.bookmarkList.get(1).getId().intValue())))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[1].bookmark.uri" ,is("http://bookmark.com/2/" + userName)))
                .andExpect(jsonPath("$._embedded.bookmarkResourceList[1].bookmark.description", is("A description 2")));

    }

    @Ignore
    @Test
    public void createBookmark() throws Exception {
        String bookmarkJson = json(new Bookmark(this.account, "http://spring.io", "spring website"));

        mockMvc.perform(post("/" + userName + "/bookmarks")
                .contentType(contentTypeHyperMedia)
                .content(bookmarkJson))
                .andExpect(status().isCreated());

    }



    protected String json(Object obj) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(obj, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


}
