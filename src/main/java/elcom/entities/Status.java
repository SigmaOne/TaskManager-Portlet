package elcom.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.awt.*;

@Entity
@Table(name="statuses")
public class Status implements Serializable {
    private long id;
    private String name;
    private Color color;
    public Status(){}

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    @Basic
    @Column(name="name")
    public String getName() {
        return name;
    }
    @Transient
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) { this.color = color; }
    @Column(name="color")
    public String getColorAsString() {
        return Integer.toHexString(color.getRed()).toUpperCase()
                .concat(Integer.toHexString(color.getGreen()).toUpperCase())
                .concat(Integer.toHexString(color.getBlue()).toUpperCase());
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setColorAsString(String colorString) {
        int red = Integer.parseInt(colorString.substring(0, 2), 16);
        int green = Integer.parseInt(colorString.substring(2, 4), 16);
        int blue = Integer.parseInt(colorString.substring(4, 6), 16);

        color = new Color(red, green, blue);
    }


    @Override
    public int hashCode() {
        int hash = (int)id * 277;
        hash += name.hashCode();
        hash += color.hashCode();

        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Status))
            return false;

        Status other = (Status) obj;

        if (this.id != other.id)
            return false;
        if (!(this.name.equals(other.name)))
            return false;
        if (!(this.color.equals(other.color)))
            return false;

        return true;
    }
    @Override
    public String toString() {
        return name;
    }
}
