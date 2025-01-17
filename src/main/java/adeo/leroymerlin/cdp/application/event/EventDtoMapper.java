package adeo.leroymerlin.cdp.application.event;

import adeo.leroymerlin.cdp.application.BandDto;
import adeo.leroymerlin.cdp.application.MemberDto;
import adeo.leroymerlin.cdp.domain.BandVO;
import adeo.leroymerlin.cdp.domain.MemberVO;
import adeo.leroymerlin.cdp.domain.event.EventVO;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class EventDtoMapper {

    private EventDtoMapper() {}

    public static EventDto toDto(EventVO event) {
        if ( event == null ) {
            return null;
        }

        return new EventDto( event.id(), event.title(), event.imgUrl(), bandVOSetToBandDtoSet( event.bands() ), event.nbStars(), event.comment() );
    }

    private static MemberDto memberVOToMemberDto(MemberVO memberVO) {
        if ( memberVO == null ) {
            return null;
        }

        return new MemberDto( memberVO.id(), memberVO.name() );
    }

    private static Set<MemberDto> memberVOSetToMemberDtoSet(Set<MemberVO> memberVOS) {
        if ( memberVOS == null ) {
            return Collections.emptySet();
        }

        Set<MemberDto> memberDtos = LinkedHashSet.newLinkedHashSet( memberVOS.size() );
        for ( MemberVO memberVO : memberVOS ) {
            memberDtos.add( memberVOToMemberDto( memberVO ) );
        }

        return memberDtos;
    }

    private static BandDto bandVOToBandDto(BandVO bandVO) {
        if ( bandVO == null ) {
            return null;
        }

        return new BandDto( bandVO.id(), bandVO.name(), memberVOSetToMemberDtoSet( bandVO.members() ) );
    }

    private static Set<BandDto> bandVOSetToBandDtoSet(Set<BandVO> bandVOS) {
        if ( bandVOS == null ) {
            return Collections.emptySet();
        }

        Set<BandDto> bandDtos = LinkedHashSet.newLinkedHashSet( bandVOS.size() );
        for ( BandVO bandVO : bandVOS ) {
            bandDtos.add( bandVOToBandDto( bandVO ) );
        }

        return bandDtos;
    }

    public static EventVO toVO(EventDto eventDto) {
        if ( eventDto == null ) {
            return null;
        }

        return new EventVO( eventDto.id(), eventDto.title(), eventDto.imgUrl(), bandDtoSetToBandVOSet( eventDto.bands() ), eventDto.nbStars(), eventDto.comment() );
    }

    private static Set<BandVO> bandDtoSetToBandVOSet(Set<BandDto> bandDtos) {
        if ( bandDtos == null ) {
            return Collections.emptySet();
        }

        Set<BandVO> bandVOS = LinkedHashSet.newLinkedHashSet( bandDtos.size() );
        for ( BandDto bandDto : bandDtos ) {
            bandVOS.add( bandDtoToBandVO( bandDto ) );
        }

        return bandVOS;
    }

    private static BandVO bandDtoToBandVO(BandDto bandDto) {
        if ( bandDto == null ) {
            return null;
        }

        return new BandVO( bandDto.id(), bandDto.name(), memberDtoSetToMemberVOSet( bandDto.members() ) );
    }

    private static Set<MemberVO> memberDtoSetToMemberVOSet(Set<MemberDto> memberDtos) {
        if ( memberDtos == null ) {
            return Collections.emptySet();
        }

        Set<MemberVO> memberVOS = LinkedHashSet.newLinkedHashSet( memberDtos.size() );
        for ( MemberDto memberDto : memberDtos ) {
            memberVOS.add( memberDtoToMemberVO( memberDto ) );
        }

        return memberVOS;
    }

    private static MemberVO memberDtoToMemberVO(MemberDto memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        return new MemberVO( memberDto.id(), memberDto.name() );
    }
}
