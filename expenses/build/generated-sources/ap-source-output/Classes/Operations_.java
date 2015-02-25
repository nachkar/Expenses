package Classes;

import Classes.Categories;
import Classes.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-29T10:47:16")
@StaticMetamodel(Operations.class)
public class Operations_ { 

    public static volatile SingularAttribute<Operations, Integer> amout;
    public static volatile SingularAttribute<Operations, String> description;
    public static volatile SingularAttribute<Operations, Integer> id;
    public static volatile SingularAttribute<Operations, Users> userid;
    public static volatile SingularAttribute<Operations, Categories> categoryid;

}