package adeo.leroymerlin.cdp.domain.event;



import adeo.leroymerlin.cdp.domain.BandVO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

public record EventVO(Long id, String title, String imgUrl, Set<BandVO> bands, Integer nbStars, String comment) {
    public EventVO withComment(String newComment) {
        return new EventVO(this.id, this.title, this.imgUrl, this.bands, this.nbStars, newComment);
    }

    public EventVO withNbStars(Integer newNbStars) {
        return new EventVO(this.id, this.title, this.imgUrl, this.bands, newNbStars, this.comment);
    }

    public boolean containsMemberMatchingName(String query) {
        return this.bands().stream()
                .anyMatch(band -> band.containsMemberMatchingName(query));
    }

    public EventVO withBands(Set<BandVO> bands) {
        return new EventVO(this.id, this.title, this.imgUrl, bands, this.nbStars, this.comment);
    }

    public EventVO withTitle(String title) {
        return new EventVO(this.id, title, this.imgUrl, this.bands, this.nbStars, this.comment);
    }

    @JsonIgnore
    public boolean isNbStarsValid() {
        return nbStars == null || (nbStars >= 0 && nbStars <= 5);
    }
}
