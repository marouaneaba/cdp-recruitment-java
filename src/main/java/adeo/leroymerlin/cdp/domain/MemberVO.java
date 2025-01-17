package adeo.leroymerlin.cdp.domain;


import org.apache.logging.log4j.util.Strings;

public record MemberVO(Long id, String name) {
    public boolean containsName(String query) {
        if (Strings.isBlank(query)) {
            return false;
        }

        return this.name.toLowerCase().contains(query.toLowerCase());
    }
}
