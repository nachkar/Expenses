package Classes;

import Classes.Operations;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-29T10:47:16")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, String> firstName;
    public static volatile SingularAttribute<Users, String> lastName;
    public static volatile CollectionAttribute<Users, Operations> operationsCollection;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile SingularAttribute<Users, Integer> flag;
    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, String> username;

}