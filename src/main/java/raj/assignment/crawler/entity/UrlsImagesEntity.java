package raj.assignment.crawler.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="UrlsImages")
@Getter
@Setter
public class UrlsImagesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UrlImageId")
    private int urlImageId;

    @Column(name = "ImagePath")
    private String imagePath;

    @Column(name = "UrlId")
    private int urlId;

    @Column(name = "StatusId")
    private int statusId;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "ModifiedDate")
    private Date modifiedDate;

}
