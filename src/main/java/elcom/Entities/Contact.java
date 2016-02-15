package elcom.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="contact")
@NamedQueries({
        @NamedQuery(name="select all organisations", query="select c from Contact c where c.organisation = true"),
        @NamedQuery(name="select all persons", query="select c from Contact c where c.person = true"),
        @NamedQuery(name="select all contacts", query="select c from Contact c")
})
public class Contact {
    private long id;
    private String content;
    private List<Contact> contacts;
    private Boolean organisation;
    private Boolean person;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    @OneToMany
    @JoinTable(name = "contact_links",
            joinColumns = @JoinColumn(name="parent_contact_id"),
            inverseJoinColumns = @JoinColumn(name="child_contact_id"))
    public List<Contact> getContacts() {
        return contacts;
    }
    @Basic
    @Column(name="value")
    public String getContent() {
        return content;
    }
    @Basic
    @Column(name="organization")
    public Boolean getOrganisation() {
        return organisation;
    }
    @Basic
    @Column(name="person")
    public Boolean getPerson() {
        return person;
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setOrganisation(Boolean organisation) {
        this.organisation = organisation;
    }
    public void setPerson(Boolean person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        int hash = (int) id;
        hash += contacts.hashCode();
        hash += content.hashCode();
        hash += organisation.hashCode();
        hash += person.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Contact))
            return false;

        Contact other = (Contact) obj;

        if (this.id != other.id) return false;
        if (!(this.content.equals(other.content))) return false;
        if (!(this.organisation.equals(other.organisation))) return false;
        if (!(this.person.equals(other.organisation))) return false;

        return true;
    }

    @Override
    public String toString() {
        return content;
    }
}