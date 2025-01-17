package adeo.leroymerlin.cdp.infrastructure.event;

import adeo.leroymerlin.cdp.domain.BandVO;
import adeo.leroymerlin.cdp.domain.MemberVO;
import adeo.leroymerlin.cdp.domain.event.EventVO;
import adeo.leroymerlin.cdp.infrastructure.Band;
import adeo.leroymerlin.cdp.infrastructure.Member;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class EventEntityMapper {

    private EventEntityMapper() {}

    public static EventVO toEventVO(Event eventEntity) {
        if ( eventEntity == null ) {
            return null;
        }

        return new EventVO( eventEntity.getId(), eventEntity.getTitle(), eventEntity.getImgUrl(), bandEntitySetToBandVOSet( eventEntity.getBands() ), eventEntity.getNbStars(), eventEntity.getComment() );
    }

    private static MemberVO memberEntityToMemberVO(Member memberEntity) {
        if ( memberEntity == null ) {
            return null;
        }

        return new MemberVO( memberEntity.getId(), memberEntity.getName() );
    }

    private static Set<MemberVO> memberEntitySetToMemberVOSet(Set<Member> memberEntities) {
        if ( memberEntities == null ) {
            return Collections.emptySet();
        }

        Set<MemberVO> members = LinkedHashSet.newLinkedHashSet( memberEntities.size() );
        for ( Member memberEntity : memberEntities ) {
            members.add( memberEntityToMemberVO( memberEntity ) );
        }

        return members;
    }

    private static BandVO bandEntityToBandVO(Band bandEntity) {
        if ( bandEntity == null ) {
            return null;
        }

        return new BandVO( bandEntity.getId(), bandEntity.getName(), memberEntitySetToMemberVOSet( bandEntity.getMembers() ) );
    }

    private static Set<BandVO> bandEntitySetToBandVOSet(Set<Band> bandEntities) {
        if ( bandEntities == null ) {
            return Collections.emptySet();
        }

        Set<BandVO> bandVOS = LinkedHashSet.newLinkedHashSet( bandEntities.size() );
        for ( Band bandEntity : bandEntities ) {
            bandVOS.add( bandEntityToBandVO( bandEntity ) );
        }

        return bandVOS;
    }

    public static Event toEventEntity(EventVO eventVO) {
        if ( eventVO == null ) {
            return null;
        }

        return new Event(eventVO.id(), eventVO.title(), eventVO.imgUrl(), bandVOSetToBandSet( eventVO.bands() ), eventVO.nbStars(), eventVO.comment());
    }

    private static Set<Band> bandVOSetToBandSet(Set<BandVO> bandVOS) {
        if ( bandVOS == null ) {
            return Collections.emptySet();
        }

        Set<Band> bands = LinkedHashSet.newLinkedHashSet( bandVOS.size() );
        for ( BandVO bandVO : bandVOS ) {
            bands.add( bandVOToBand( bandVO ) );
        }

        return bands;
    }

    private static Band bandVOToBand(BandVO bandVO) {
        if ( bandVO == null ) {
            return null;
        }
        return new Band(bandVO.id(), bandVO.name(), memberVOSetToMemberSet( bandVO.members() ));
    }

    private static Set<Member> memberVOSetToMemberSet(Set<MemberVO> memberVOS) {
        if ( memberVOS == null ) {
            return Collections.emptySet();
        }

        Set<Member> members = LinkedHashSet.newLinkedHashSet( memberVOS.size() );
        for ( MemberVO memberVO : memberVOS ) {
            members.add( memberVOToMember( memberVO ) );
        }

        return members;
    }

    private static Member memberVOToMember(MemberVO memberVO) {
        if ( memberVO == null ) {
            return null;
        }

        return new Member(memberVO.id(), memberVO.name());
    }

}
