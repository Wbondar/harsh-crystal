package pl.chelm.pwsz.harsh_crystal;

import java.beans.PropertyDescriptor;

/**
 * This class is to extends for all builders of the application: Board instance
 * builders and Simulation instance builders.
 * 
 * Builder class is introduced to provide unified interface for all programmers.
 * 
 * It enforces JavaBeans convention on all of the descendants.
 * 
 * @author Vladyslav Bondarenko
 *
 * @param <T>
 *            type of entity this builder builds.
 */
public abstract class Builder<T>
{
    /**
     * Constructs an instance from provided properties. Is to be
     * overriden by the descendants.
     * 
     * @return instance of type
     */
    public abstract T build();

    /**
     * Delegates retrieval of value mapped by provided key to according read
     * method of the class. Is to remain consistent for all implementations.
     * 
     * @param key
     *            name of property to retrieve value of
     * @param <V> cast using generics      
     * @return null if property wasn't found
     */
    public final <V> V getProperty(final String key)
    {
        try
        {
            return (V) (new PropertyDescriptor(key, this.getClass()))
                    .getReadMethod().invoke(this);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delegates assigning of the provided value to according write method of
     * the class. Does nothing if something went wrong. Is to remain consistent
     * for all implementations.
     * 
     * @param key
     *            name of property to assing value to
     * @param value
     *            value to assign to given key
     * @param <V> cast using generics      
     * @return always instance of Builder class
     */
    public final <V> Builder<T> setProperty(String key, final V value)
    {
        try
        {
            (new PropertyDescriptor(key, this.getClass())).getWriteMethod()
                    .invoke(this, value);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return this;
    }
}
