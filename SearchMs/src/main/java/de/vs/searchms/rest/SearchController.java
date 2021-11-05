package de.vs.searchms.rest;

import de.vs.searchms.rest.dto.SearchResult;
import de.vs.searchms.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class SearchController {

    private final SearchService service;

    public SearchController(SearchService service) {
        this.service = service;
    }

    @GetMapping("/search")
    public List<SearchResult> getSearchResult(@RequestParam(required = false, defaultValue = "-1.0") double minPrice,
                                              @RequestParam(required = false, defaultValue = "-1.0") double maxPrice,
                                              @RequestParam(required = false, defaultValue = "") String description) {
        return service.search(minPrice, maxPrice, description);
    }
}
