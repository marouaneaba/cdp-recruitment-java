package adeo.leroymerlin.cdp.domain;


import java.util.Set;

public record BandVO(Long id, String name, Set<MemberVO> members) {

    public boolean containsMemberMatchingName(String query) {
        return this.members().stream()
                .anyMatch(member -> member.containsName(query));
    }

    public BandVO withMembers(Set<MemberVO> members) {
        return new BandVO(this.id, this.name, members);
    }

    public BandVO withMembers(String name) {
        return new BandVO(this.id, name, this.members);
    }
}
