package lv.javaguru.java2.domain;

import lv.javaguru.java2.database.CommentDAO;

import javax.persistence.*;

/**
 * Created by Anton on 2015.03.01..
 */

@Entity
@Table(name = "comments")
public class Comment {

//    CREATE TABLE IF NOT EXISTS `java2_test`.`comments` (
//            `ID` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
//    `Comment` CHAR(255) NULL DEFAULT NULL,
//    `UserID` INT(11) NOT NULL,
//    `ProductID` INT(11) NOT NULL,
//    PRIMARY KEY (`ID`, `UserID`, `ProductID`),
//    FOREIGN KEY (`UserID`) REFERENCES `java2_test`.`users` (`UserID`)
//    ON DELETE CASCADE
//    ON UPDATE CASCADE,
//    FOREIGN KEY (`ProductID`) REFERENCES `java2_test`.`products` (`ProductID`)
//    ON DELETE CASCADE
//    ON UPDATE CASCADE)
//    ENGINE = InnoDB
//    DEFAULT CHARACTER SET = utf8;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID",columnDefinition = "INT(11)")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name="ProductID",columnDefinition = "INT(11)")
    private Long productID;

    @Column(name = "Comment", columnDefinition = "CHAR(255)")
    private String comment;

    public Comment() {

    }

    public Comment(User user, Long productID, String comment){
        this.comment = comment;
        this.productID = productID;
        this.user = user;
    }

    public Long getUserID() { return user.getId(); }

    public Long getId() { return id; }
    public void setId(Long value) { id = value; }

    public Long getProductID() { return productID; }
    public void setProductID(Long value) { productID = value; }

    public String getComment() { return comment; }
    public void setComment(String value) { comment = value; }

    public String getUsername() { return user.getLogin(); }
}
