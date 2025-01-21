package adeo.leroymerlin.cdp.application.event;

import adeo.leroymerlin.cdp.application.BandDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventDto(Long id, String title, String imgUrl, Set<BandDto> bands, Integer nbStars, String comment) { }
