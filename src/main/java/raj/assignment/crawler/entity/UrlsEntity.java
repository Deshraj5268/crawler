package raj.assignment.crawler.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Urls")
@Setter
@Getter
public class UrlsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "UrlId")
    private int urlId;

    @Column(name = "Name")
    private String name;

    @Column(name = "ParentUrlId")
    private int parentUrlId;

    @Column(name = "HierarchyName")
    private String hierarchyName;

    @Column(name = "OrgHierarchyId")
    private String orgHierarchyId;

    @Column(name = "HierarchyLevel")
    private int hierarchyLevel;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "ModifiedDate")
    private Date modifiedDate;

}
