package org.anthony.library.repository.entity;

import java.util.Objects;

import org.anthony.tablePrinter.TablePrinter.Column;

public class MemberEntity {
    private Integer libraryCard;
    private String memberPassword;
    private String memberName;

    public MemberEntity(Integer libraryCard, String memberPassword, String memberName) {
        this.libraryCard = libraryCard;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
    }

    public MemberEntity() {
    }

    @Column(name="card_number", weight=1)
    public Integer getLibraryCard() {
        return libraryCard;
    }

    public void setLibraryCard(Integer libraryCard) {
        this.libraryCard = libraryCard;
    }

    @Column(name="password", width=20, weight=2)
    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    @Column(name="name", width=20, weight=3)
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.libraryCard);
        hash = 71 * hash + Objects.hashCode(this.memberPassword);
        hash = 71 * hash + Objects.hashCode(this.memberName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MemberEntity other = (MemberEntity) obj;
        if (!Objects.equals(this.memberPassword, other.memberPassword)) {
            return false;
        }
        if (!Objects.equals(this.memberName, other.memberName)) {
            return false;
        }
        return Objects.equals(this.libraryCard, other.libraryCard);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Member{");
        sb.append("libraryCard=").append(libraryCard);
        sb.append(", memberPassword=").append(memberPassword);
        sb.append(", memberName=").append(memberName);
        sb.append('}');
        return sb.toString();
    }
}
