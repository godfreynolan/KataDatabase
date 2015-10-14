package com.riis.katadatabase;

/**
 * Created by Godfrey on 10/12/2015.
 */
public interface UserDAO {

    public long getId();
    public void setId(int id);
    public String getName();
    public void setName(String name);
    public String getEmail();
    public void setEmail(String email);

}
