package org.anthony.library.service_layer.model;

import org.anthony.library.repository.entity.MemberEntity;

public class Member extends MemberEntity {
    public Member(MemberEntity o)
    {
        super(o.getLibraryCard(), o.getMemberPassword(), o.getMemberName());
    }
}
