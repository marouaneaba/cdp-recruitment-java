package adeo.leroymerlin.cdp.application;




import java.util.Set;

public record BandDto (Long id, String name, Set<MemberDto> members) { }
